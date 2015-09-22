package com.guide.action;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class GetActionByNotificationIdRequest extends VolleyRequest<GetActionResult> {
    public GetActionByNotificationIdRequest(String msgId, Callbacks callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/actions/msgId/%s", msgId), callbacks);
    }

    @Override
    protected GetActionResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
