package com.guide.group.model;

import com.guide.base.SimpleResult;
import com.guide.user.model.Tourist;

import java.util.List;

/**
 * Created by mac on 7/9/15.
 */
public class GetTouristByGroupIdResult extends SimpleResult {
    private List<Tourist> tourists;

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<Tourist> tourists) {
        this.tourists = tourists;
    }
}
