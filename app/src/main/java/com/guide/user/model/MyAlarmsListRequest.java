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
public class MyAlarmsListRequest extends VolleyRequest<List<Alarm>> {
    public MyAlarmsListRequest(Callbacks<List<Alarm>> callbacks) {
        super(Request.Method.GET, Consts.API_URL + "/tourists/alarms", callbacks);
    }

    @Override
    protected List<Alarm> convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject().get("activeAlarms"));
    }
}
