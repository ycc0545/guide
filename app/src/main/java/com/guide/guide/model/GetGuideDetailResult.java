package com.guide.guide.model;

import com.guide.base.SimpleResult;
import com.guide.group.model.GuideInfo;

/**
 * Created by mac on 19/9/15.
 */
public class GetGuideDetailResult extends SimpleResult {
    private GuideInfo guideInfo;

    public GuideInfo getGuideInfo() {
        return guideInfo;
    }

    public void setGuideInfo(GuideInfo guideInfo) {
        this.guideInfo = guideInfo;
    }
}
