package com.guide.base;

import java.io.Serializable;

public class Result implements Serializable {

    public static final int CODE_OK = 200;
    public static final int CODE_NEW_APK_AVAILABLE_FORCE = 200010;
    public static final int CODE_NEW_APK_AVAILABLE = 200011;
    public static final int CODE_NEED_SET_WITHDRAW_PWD = 500529;
    public static final int CODE_NEED_SET_SECRET_QUEST = 500530;

    public static int CODE_ERR = -1;
    private int resCode;
    private String errMsg;

    public String toString() {
        return "resCode = " + resCode + ", errMsg=" + errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getResCode() {
        return resCode;
    }
}
