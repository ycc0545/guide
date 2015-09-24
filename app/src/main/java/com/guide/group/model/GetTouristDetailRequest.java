package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class GetTouristDetailRequest extends VolleyRequest<GetTouristDetailResult> {
    public GetTouristDetailRequest(int groupId, int touristId, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/guides/groups/%d/tourists/%d", groupId, touristId), callbacks);
    }

    @Override
    protected GetTouristDetailResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
