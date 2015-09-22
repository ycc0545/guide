package com.guide.guide.model;

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
public class EditGuideRequest extends VolleyRequestPost<SimpleResult> {
    public EditGuideRequest(int type, String key1, String key2, String key3, String value1, String value2, String value3, Callbacks<SimpleResult> callbacks) {
        super(Request.Method.PUT, Consts.API_URL + "/tourists", callbacks);


        HashMap<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        map.put(key1, value1);
        if (value2 != "") {
            map.put(key2, value2);
        }
        if (value3 != "") {
            map.put(key3, value3);
        }
        setParams(map);
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
