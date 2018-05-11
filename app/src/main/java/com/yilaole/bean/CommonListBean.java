package com.yilaole.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 统一接收数据的bean
 */

public class CommonListBean<T> implements Serializable {
    int code;
    String message;
    List<T> result;

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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CommonListBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
