package com.yilaole.inter_face.ilistener;

/**
 * 上拉 下拉 初始获取的统一监听
 */

public interface OnMoreAndRefreshListener {

    void onListSuccess(Object bean);

    void onListFailed(int code, String msg, Exception e);

    void onRefreshSuccess(Object bean);

    void onRefreshFailed(int code, String msg, Exception e);

    void onMoreSuccess(Object bean);

    void onMoreFailed(int code, String msg, Exception e);
}
