package com.guide.base;

import java.io.Serializable;

/**
 * example:
 * {
 * "result": {
 * "resCode": 1, // 返回1:存在;返回－1:不存在
 * "errMsg": "" // 失败信息
 * }
 * }
 */

public class SimpleResult implements Serializable {
    private Result result;

    public static int getResCode(SimpleResult simpleResult) {
        if (simpleResult == null) {
            return Result.CODE_ERR;
        } else {
            return simpleResult.getResCode();
        }

    }

    public static String getErrMsg(SimpleResult simpleResult) {
        if (simpleResult != null) {
            return simpleResult.getErrMsg();
        }
        return "";
    }

    public String toString() {
        return result.toString();
    }

    public String getErrMsg() {
        if (result == null) {
            return "";
        }
        return result.getErrMsg();
    }

    public int getResCode() {
        if (result == null) {
            return Result.CODE_ERR;
        } else {
            return result.getResCode();
        }

    }

    public boolean isResCodeOK() {
        return getResCode() == Result.CODE_OK;
    }
}
