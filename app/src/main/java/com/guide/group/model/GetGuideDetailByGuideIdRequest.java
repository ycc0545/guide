package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class GetGuideDetailByGuideIdRequest extends VolleyRequest<GetGuideDetailByGuideIdResult> {
    public GetGuideDetailByGuideIdRequest(int guideId, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/tourists/guides/%d", guideId), callbacks);
    }

    @Override
    protected GetGuideDetailByGuideIdResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
