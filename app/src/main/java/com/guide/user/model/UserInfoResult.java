package com.guide.user.model;

import com.guide.base.SimpleResult;

/**
 * Created by Xingkai on 14/9/15.
 */
public class UserInfoResult extends SimpleResult {
    private Tourist tourist;

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }
}
