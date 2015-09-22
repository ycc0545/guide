package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mac on 2/9/15.
 */
public class LoginRequest extends VolleyRequestPost<LoginResult> {
    public LoginRequest(String userName, String password, Callbacks callbacks) {
        super(Request.Method.POST, Consts.API_URL + "/login", callbacks);
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);
        setParams(map);
    }

    @Override
    protected LoginResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
