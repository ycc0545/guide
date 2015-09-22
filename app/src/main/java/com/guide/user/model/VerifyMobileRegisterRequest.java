package com.guide.user.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

public class VerifyMobileRegisterRequest extends VolleyRequest<UserInfoResult> {
    public VerifyMobileRegisterRequest(VolleyRequest.Callbacks<UserInfoResult> callbacks, String phoneNum) {
        super(Request.Method.GET, Consts.API_URL + String.format("/tourists/mobile/%s", phoneNum), callbacks);
    }

    @Override
    protected UserInfoResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
