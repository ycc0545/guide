package com.guide.action;

import com.guide.base.SimpleResult;

/**
 * Created by mac on 2/9/15.
 */
public class GetActionResult extends SimpleResult {
    private Item action;

    public Item getAction() {
        return action;
    }

    public void setAction(Item action) {
        this.action = action;
    }
}
