package com.guide.base;

import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.guide.Consts;

import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class VolleyRequest<T> implements Pagination {

    protected static final JsonParser parser = new JsonParser();
    protected static final Gson gson = GsonProvider.getInstance().get();

    private int method;
    private String url;
    private Callbacks<T> callbacks;
    private final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (callbacks != null) {
                callbacks.onError(volleyError);

            }
        }
    };
    private int offset;
    private int limit;

    public VolleyRequest(int method, String url, Callbacks<T> callbacks) {
        this.method = method;
        this.url = url;
        this.callbacks = callbacks;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;

    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;

    }

    public VolleyRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public Request<T> createRequest() {
        // handle pagination
        if (limit != 0) {
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter(Pagination.OFFSET, String.valueOf(offset));
            builder.appendQueryParameter(Pagination.LIMIT, String.valueOf(limit));
            url = builder.toString();

        }

        return new Request<T>(method, url, errorListener) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {
                try {
                    return Response.success(convert(response), HttpHeaderParser.parseCacheHeaders(response));
                } catch (IOException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(T t) {
                if (callbacks != null) {
                    callbacks.onResponse(t);
                }
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> body = postBody();
                if (body == null) {
                    return super.getBody();
                } else {
                    try {
                        return gson.toJson(body).getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }

                }

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = postParams();
                if (params == null) {
                    return super.getParams();
                } else {
                    return params;
                }


            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = headers();
                if (headers == null) {
                    return super.getHeaders();
                } else {
                    return headers;
                }
            }
        };
    }

    protected Map<String, String> postParams() {
        return null;
    }

    protected Map<String, String> postBody() {
        return null;
    }

    protected Map<String, String> headers() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json;charset=UTF-8");
        if (LoginManager.getInstance().isLogin()) {
            map.put(Consts.KEY_TOKEN, LoginManager.getInstance().getToken());
        }
        map.put(Consts.CLIENT_ID, "app");
        return map;
    }

    protected T convert(NetworkResponse response) throws IOException {
        String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        Log.i("DEBUG", "json = " + json);
        JsonElement rootElement = parser.parse(json);
        if (!rootElement.isJsonObject()) {
            throw new JsonParseException("Root is not JsonObject");
        }

        JsonObject rootJsonObject = rootElement.getAsJsonObject();
        if (rootJsonObject.has("result")) {
            Result result = gson.fromJson(rootJsonObject.get("result"), Result.class);
            if (result != null && !String.valueOf(result.getResCode()).startsWith("200")
                    && result.getResCode() != 500522) {
                if (result.getResCode() == Consts.CODE_TOKEN_EXPIRE) {
                    //TODO
//                    LoginManager.getInstance().exitLogin();
                } else {
                    throw new HttpResponseException(result.getResCode(), result.getErrMsg());
                }
            }
        }


        return convert(rootElement);


    }

    protected T convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }

    protected T convertDataElement(JsonElement data) {
        return gson.fromJson(data, getType());
    }

    protected Type getType() {
        Type superclass = getClass().getGenericSuperclass();
        while ((superclass instanceof Class) && !superclass.equals(VolleyRequest.class)) {
            superclass = ((Class) superclass).getGenericSuperclass();
        }
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }


    public interface Callbacks<T> {
        void onResponse(T t);

        void onError(VolleyError error);
    }

}
