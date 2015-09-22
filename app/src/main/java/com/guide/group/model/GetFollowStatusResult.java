package com.guide.group.model;

import com.guide.base.SimpleResult;

/**
 * Created by mac on 7/9/15.
 */
public class GetFollowStatusResult extends SimpleResult {
    private boolean followed;

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
