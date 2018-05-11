package com.yilaole.inter_face.ipresenter;

/**
 * 注册的p层
 */

public interface IRegPresenter {

    void pRegist(String user_phone, String ps, String code);

    void pGetCode(String user_phone);

}
