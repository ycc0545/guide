package com.guide.base;

import android.text.TextUtils;

public class LoginManager {
    private static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public boolean isLogin() {
        return (!TextUtils.isEmpty(getToken()));
    }

    public String getToken() {
        return Keeper.readToken();
    }

    public void setToken(String token) {
        Keeper.keepToken(token);
    }
}
