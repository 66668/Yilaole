package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 注册的code的bean
 */

public class RegCodeBean implements Serializable {
    int code;
    String message;
    String Result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "RegCodeBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }
}
