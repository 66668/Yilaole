package com.yilaole.inter_face.ilistener;

/**
 * 单一接口act的统一数据调用监听
 */

public interface OnLoginListener {

    void onLogSuccess(Object obj);

    void onLogFailed(int code,String msg, Exception e);
}
