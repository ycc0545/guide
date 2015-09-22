package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class GetGroupByGuideRequest extends VolleyRequest<List<Group>> {
    public GetGroupByGuideRequest(int guideId, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/groups/guides/%d", guideId), callbacks);
    }

    @Override
    protected List<Group> convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject().get("groupList"));
    }
}
