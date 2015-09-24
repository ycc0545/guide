package com.guide.group.model;

import com.guide.action.Tourist;
import com.guide.base.SimpleResult;

/**
 * Created by mac on 7/9/15.
 */
public class GetTouristDetailResult extends SimpleResult {
    private Tourist touristInfo;

    public Tourist getTouristInfo() {
        return touristInfo;
    }

    public void setTouristInfo(Tourist touristInfo) {
        this.touristInfo = touristInfo;
    }
}
