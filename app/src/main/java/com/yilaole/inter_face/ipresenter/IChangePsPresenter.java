package com.yilaole.inter_face.ipresenter;

/**
 *  修改密码 p层
 */

public interface IChangePsPresenter {

    void pChangePsPost(String token, String oldPs, String newPs);

}
