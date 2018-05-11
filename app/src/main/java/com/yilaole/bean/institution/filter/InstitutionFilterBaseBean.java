package com.yilaole.bean.institution.filter;

import java.io.Serializable;

/**
 * 筛选条件
 */

public class InstitutionFilterBaseBean implements Serializable {
    int code;
    String message;
    FilterBean result;

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

    public FilterBean getResult() {
        return result;
    }

    public void setResult(FilterBean result) {
        this.result = result;
    }
}
