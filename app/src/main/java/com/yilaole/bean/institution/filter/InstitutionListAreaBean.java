package com.yilaole.bean.institution.filter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 省 市 区的bean
 */

public class InstitutionListAreaBean implements Serializable {
    int code;
    String message;
    ArrayList<ProvinceBean> result;

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

    public ArrayList<ProvinceBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<ProvinceBean> result) {
        this.result = result;
    }
}
