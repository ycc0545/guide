package com.guide.user.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class MyFollowsListRequest extends VolleyRequest<List<Follow>> {
    public MyFollowsListRequest(Callbacks<List<Follow>> callbacks) {
        super(Request.Method.GET, Consts.API_URL + "/tourists/follows", callbacks);
    }

    @Override
    protected List<Follow> convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject().get("follows"));
    }
}
