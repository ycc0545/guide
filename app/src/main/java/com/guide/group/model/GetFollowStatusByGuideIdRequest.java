package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class GetFollowStatusByGuideIdRequest extends VolleyRequest<GetFollowStatusResult> {
    public GetFollowStatusByGuideIdRequest(int guideId, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/tourists/%d/followStatus", guideId), callbacks);
    }

    @Override
    protected GetFollowStatusResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
