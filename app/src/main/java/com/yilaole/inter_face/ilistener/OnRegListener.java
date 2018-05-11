package com.yilaole.inter_face.ilistener;

/**
 * 注册接回调
 */

public interface OnRegListener {

    void onRegSuccess(Object obj);

    void onRegFailed(int code, String msg, Exception e);

    void onCodeSuccess(Object object);

    void onCodeFailed(int code, String msg, Exception e);
}
