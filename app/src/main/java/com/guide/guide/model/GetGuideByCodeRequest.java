package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class GetGuideByCodeRequest extends VolleyRequest<GetGuideByCodeResult> {
    public GetGuideByCodeRequest(String code, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/guides/code/%s", code), callbacks);
    }

    @Override
    protected GetGuideByCodeResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
