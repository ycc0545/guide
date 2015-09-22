package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class GetTouristByGroupIdRequest extends VolleyRequest<GetTouristByGroupIdResult> {
    public GetTouristByGroupIdRequest(int groupId, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/groups/%d/tourists", groupId), callbacks);
    }

    @Override
    protected GetTouristByGroupIdResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
