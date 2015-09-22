package com.guide.deal.model;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.VolleyRequest;

import java.io.IOException;

/**
 * Created by mac on 2/9/15.
 */
public class SearchDealListRequest extends VolleyRequest<DealListResult> {
    public SearchDealListRequest(String dealName, Callbacks<DealListResult> callbacks) {
        super(Request.Method.GET, String.format(Consts.API_URL + "/deals/names/%s", dealName), callbacks);
    }

    @Override
    protected DealListResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}
