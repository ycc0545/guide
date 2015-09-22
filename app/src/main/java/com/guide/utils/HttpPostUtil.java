package com.guide.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.guide.Consts;
import com.guide.base.GsonProvider;
import com.guide.base.LoginManager;
import com.guide.base.Result;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Xingkai on 20/9/15.
 */
public class HttpPostUtil {
    protected static final JsonParser parser = new JsonParser();
    protected static final Gson gson = GsonProvider.getInstance().get();

    public static JsonObject httpPostData(String uri, Object object) {
        JsonObject rootJsonObject = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uri);
            httppost.addHeader("Content-Type", "application/json");
            httppost.addHeader(Consts.CLIENT_ID, "app");
            httppost.addHeader(Consts.KEY_TOKEN, LoginManager.getInstance().getToken());

            httppost.setEntity(new StringEntity(object.toString(), "utf-8"));
            HttpResponse response;
            response = httpclient.execute(httppost);

            //检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                String json = EntityUtils.toString(response.getEntity());//返回json格式
                JsonElement rootElement = parser.parse(json);
                if (!rootElement.isJsonObject()) {
                    throw new JsonParseException("Root is not JsonObject");
                }

                rootJsonObject = rootElement.getAsJsonObject();
                if (rootJsonObject.has("result")) {
                    Result result = gson.fromJson(rootJsonObject.get("result"), Result.class);
                    if (result.getResCode() != 200) {
                        throw new HttpResponseException(result.getResCode(), result.getErrMsg());
                    }
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootJsonObject;
    }
}
