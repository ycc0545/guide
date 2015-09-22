package com.guide.guide.model;

import com.guide.base.SimpleResult;
import com.guide.group.model.Guide;

/**
 * Created by mac on 19/9/15.
 */
public class GetGuideByCodeResult extends SimpleResult {
    private Guide guide;

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
