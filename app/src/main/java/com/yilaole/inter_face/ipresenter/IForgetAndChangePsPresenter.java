package com.yilaole.inter_face.ipresenter;

/**
 * 忘记密码/修改密码 获取验证码的p层
 */

public interface IForgetAndChangePsPresenter {

    void pForgetAndChangePost(String user_phone, String ps, String code);

    void pGetChangeAndForgetCode(String user_phone);

}
