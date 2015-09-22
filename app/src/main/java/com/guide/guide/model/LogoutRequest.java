package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class LogoutRequest extends VolleyRequestPost<SimpleResult> {
    public LogoutRequest(VolleyRequest.Callbacks callbacks) {
        super(Request.Method.POST, Consts.API_URL + "/logout", callbacks);
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
