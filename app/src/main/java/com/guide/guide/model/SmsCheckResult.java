package com.guide.guide.model;

import com.guide.base.SimpleResult;

/**
 * example:
 * {
 * "result": {
 * "resCode": 1, // 返回1:存在;返回－1:不存在
 * "errMsg": "" // 失败信息
 * }
 * "uuid": "xxx" // 成功则向客户端返回一个唯一识别id
 * }
 */

public class SmsCheckResult extends SimpleResult {
    private String uuid;

    public String getUUID() {
        return uuid;
    }
}
