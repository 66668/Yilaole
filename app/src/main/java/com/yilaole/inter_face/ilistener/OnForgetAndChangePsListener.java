package com.yilaole.inter_face.ilistener;

/**
 * 忘记密码 获取验证码 监听
 */

public interface OnForgetAndChangePsListener {

    void onForgetCodeSuccess(Object obj);

    void onForgetCodeFailed(int code,String msg, Exception e);

}
