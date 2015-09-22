package com.guide.guide.model;

import com.guide.base.SimpleResult;

/**
 * Created by mac on 9/9/15.
 */
public class LoginResult extends SimpleResult {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
