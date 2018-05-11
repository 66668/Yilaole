package com.yilaole.inter_face.ipresenter;

/**
 * 登录p层
 */

public interface ILoginPresenter {

    void pLoginByPhoneAndPs(String user_phone, String ps);

    void pLoginToken(String token);

}
