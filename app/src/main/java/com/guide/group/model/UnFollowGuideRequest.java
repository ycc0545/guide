package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class UnFollowGuideRequest extends VolleyRequestPost<SimpleResult> {
    public UnFollowGuideRequest(int guideId, Callbacks<SimpleResult> callbacks) {
        super(Request.Method.POST, String.format(Consts.API_URL + "/tourists/guides/%d/unFollow", guideId), callbacks);
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}