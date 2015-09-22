package com.guide.group.model;

import com.guide.base.SimpleResult;

/**
 * Created by mac on 9/9/15.
 */
public class AddGroupResult extends SimpleResult {
    private int guideId;
    private String groupCode;

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
