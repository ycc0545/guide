package com.guide.base;

import com.guide.Consts;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequestPost<T> extends VolleyRequest<T> {
    protected Map<String, String> params;

    public VolleyRequestPost(int method, String url, Callbacks<T> callbacks) {
        super(method, url, callbacks);
    }

    @Override
    protected Map<String, String> headers() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        if (LoginManager.getInstance().isLogin()) {
            header.put(Consts.KEY_TOKEN, LoginManager.getInstance().getToken());
        }
        header.put(Consts.CLIENT_ID, "guide");

        return header;
    }

    @Override
    protected Map<String, String> postParams() {
        return params;
    }

    @Override
    protected Map<String, String> postBody() {
        return params;
    }

    public void setParams(Map<String, String> p) {
        this.params = p;
    }
}
