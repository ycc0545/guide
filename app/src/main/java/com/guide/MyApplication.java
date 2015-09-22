package com.guide;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;
import com.guide.base.Keeper;
import com.guide.base.Request;

import org.apache.http.client.HttpClient;

import roboguice.RoboGuice;

public class MyApplication extends Application {
    public static final String TAG = "Volley";
    private static MyApplication instance = null;
    private RequestQueue requestQueue;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Keeper.init(this);

        HttpClient httpClient = RoboGuice.getInjector(this).getInstance(HttpClient.class);
        Request.RequestParamsFactory.setHttpClient(httpClient);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            HttpClient httpClient = RoboGuice.getInjector(this).getInstance(HttpClient.class);
            requestQueue = Volley.newRequestQueue(getApplicationContext(), new HttpClientStack(httpClient));
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
