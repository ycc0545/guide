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
public class GroupListRequest extends VolleyRequest<List<Group>> {
    public GroupListRequest(Callbacks<List<Group>> callbacks) {
        super(Request.Method.GET, Consts.API_URL + "/tourists/groups", callbacks);
    }

    @Override
    protected List<Group> convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject().get("groups"));
    }
}
