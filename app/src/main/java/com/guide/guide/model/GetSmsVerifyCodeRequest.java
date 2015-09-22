package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetSmsVerifyCodeRequest extends VolleyRequestPost<SimpleResult> {
    private static final String TAG = "sms";

    public GetSmsVerifyCodeRequest(Callbacks<SimpleResult> callbacks, String mobile) {
        super(Request.Method.POST, Consts.API_URL + "/sms/send", callbacks);

        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        params = map;
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }

}
