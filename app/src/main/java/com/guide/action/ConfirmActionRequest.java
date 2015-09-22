package com.guide.action;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mac on 2/9/15.
 */
public class ConfirmActionRequest extends VolleyRequestPost<GetActionResult> {
    public ConfirmActionRequest(int actionType, int actionId, double longitude, double latitude, Callbacks<GetActionResult> callbacks) {
        super(Request.Method.POST, Consts.API_URL + "/actions/tourists/confirm", callbacks);

        HashMap<String, String> map = new HashMap<>();
        map.put("actionType", String.valueOf(actionType));
        map.put("actionId", String.valueOf(actionId));
        map.put("longitude", String.valueOf(longitude));
        map.put("latitude", String.valueOf(latitude));
        setParams(map);
    }

    @Override
    protected GetActionResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}