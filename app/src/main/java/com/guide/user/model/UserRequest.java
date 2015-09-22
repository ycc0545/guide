package com.guide.user.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class UserRequest extends VolleyRequest<UserInfoResult> {
    public UserRequest(Callbacks<UserInfoResult> callbacks) {
        super(Request.Method.GET, Consts.API_URL + "/tourists", callbacks);
    }

    @Override
    protected UserInfoResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
