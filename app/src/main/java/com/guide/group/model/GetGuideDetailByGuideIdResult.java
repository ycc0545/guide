package com.guide.group.model;

import com.guide.base.SimpleResult;

/**
 * Created by mac on 7/9/15.
 */
public class GetGuideDetailByGuideIdResult extends SimpleResult {
    private GuideInfo guideInfo;

    public GuideInfo getGuideInfo() {
        return guideInfo;
    }

    public void setGuideInfo(GuideInfo guideInfo) {
        this.guideInfo = guideInfo;
    }
}
