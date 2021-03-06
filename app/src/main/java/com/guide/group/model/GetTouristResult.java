package com.guide.group.model;

import com.guide.action.Tourist;
import com.guide.base.SimpleResult;

import java.util.List;

/**
 * Created by mac on 7/9/15.
 */
public class GetTouristResult extends SimpleResult {
    private List<Tourist> tourists;

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<Tourist> tourists) {
        this.tourists = tourists;
    }
}
