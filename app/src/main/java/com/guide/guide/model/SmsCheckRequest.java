package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SmsCheckRequest extends VolleyRequestPost<SmsCheckResult> {
    public SmsCheckRequest(Callbacks<SmsCheckResult> callbacks, String mobile, String smsCode) {
        super(Request.Method.POST, Consts.API_URL + "/sms/check", callbacks);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("smsCode", smsCode);
        params = map;
    }

    @Override
    protected SmsCheckResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }

}
