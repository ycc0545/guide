package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mac on 2/9/15.
 */
public class RegisterRequest extends VolleyRequestPost<SimpleResult> {
    public RegisterRequest(String uuid, String userName, String realName, String mobile, String password, String pwdRepeat, String registrationId, Callbacks callbacks) {
        super(Request.Method.POST, Consts.API_URL + "/tourists", callbacks);

        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("userName", userName);
        map.put("name", realName);
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("pwdRepeat", pwdRepeat);
        map.put("registrationId", registrationId);
        setParams(map);
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
