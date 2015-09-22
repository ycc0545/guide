package com.guide.guide.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 19/9/15.
 */
public class GetGuideDetailRequest extends VolleyRequest<GetGuideDetailResult> {
    public GetGuideDetailRequest(Callbacks<GetGuideDetailResult> callbacks) {
        super(Request.Method.GET, Consts.API_URL + "/guides", callbacks);
    }

    @Override
    protected GetGuideDetailResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}