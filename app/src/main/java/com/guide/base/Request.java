package com.guide.base;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class Request<T> implements ResponseHandler<T> {
    protected static final JsonParser parser = new JsonParser();
    protected static final Gson gson = GsonProvider.getInstance().get();
    protected static final HttpClient httpClient = RequestParamsFactory.getHttpClient();


    public T execute() throws IOException {
        HttpUriRequest httpRequest = getHttpUriRequest();
        T data;
        try {
            data = httpClient.execute(httpRequest, this);
        } catch (SecurityException e) {
            throw new IOException(e.getMessage());
        }
        return data;

    }

    protected abstract HttpUriRequest getHttpUriRequest();


    @Override
    public T handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        if (httpResponse.getEntity() == null) {
            throw new IOException("Failed to get response's entity");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent(), HTTP.UTF_8));
        return convert(reader);
    }

    private T convert(Reader reader) throws IOException {
        try {
            JsonElement rootElement = parser.parse(reader);
            return convert(rootElement);
        } catch (JsonParseException jpe) {
            IOException ioe = new IOException(
                    "Parse exception converting JSON to object");
            ioe.initCause(jpe);
            throw ioe;
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }
    }

    public T convert(JsonElement rootElement) throws IOException {
        if (!rootElement.isJsonObject()) {
            throw new JsonParseException("Root is not JsonObject");
        }
        JsonObject root = rootElement.getAsJsonObject();

        if (root.has("data")) {
            JsonElement data = root.get("data");
            return convertDataElement(data);
        }
        // TODO handle http error

        throw new IOException("Server Error");
    }

    protected T convertDataElement(JsonElement data) {
        return gson.fromJson(data, getType());
    }

    protected void convertErrorElement(JsonElement error) throws HttpResponseException {
        if (error.isJsonObject()) {
            JsonObject errorObject = error.getAsJsonObject();
            int code = errorObject.has("code") ? errorObject.get(
                    "code").getAsInt() : 400;
            String message = errorObject.has("message") ? errorObject
                    .get("message").getAsString() : "";
            throw new HttpResponseException(code, message);
        }
    }


    protected Type getType() {
        Type superclass = ((Object) this).getClass().getGenericSuperclass();
        while ((superclass instanceof Class) && !superclass.equals(Request.class)) {
            superclass = ((Class) superclass).getGenericSuperclass();
        }
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    protected static HttpUriRequest buildFormEntityRequest(String uri, List<BasicNameValuePair> params) {
        HttpEntityEnclosingRequestBase request = new HttpPost(uri);
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(entity);
        return request;
    }

    public static class RequestParamsFactory {
        private static HttpClient httpClient;

        public static HttpClient getHttpClient() {
            return httpClient;
        }

        public static void setHttpClient(HttpClient httpClient) {
            RequestParamsFactory.httpClient = httpClient;
        }
    }
}
