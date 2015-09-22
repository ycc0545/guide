package com.guide.group.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mac on 2/9/15.
 */
public class AddEventRequest extends VolleyRequestPost<SimpleResult> {
    public AddEventRequest(int groupId, String scheduleTime, String location, String content, Callbacks<SimpleResult> callbacks) {
        super(Request.Method.POST, Consts.API_URL + "/events", callbacks);

        HashMap<String, String> map = new HashMap<>();
        map.put("groupId", String.valueOf(groupId));
        map.put("scheduleTime", scheduleTime);
        map.put("location", location);
        map.put("content", content);
        setParams(map);
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}