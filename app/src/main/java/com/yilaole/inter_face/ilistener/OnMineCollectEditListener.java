package com.yilaole.inter_face.ilistener;

/**
 * 我的收藏 编辑 接口回调
 */

public interface OnMineCollectEditListener {

    void onCarelessSuccess(Object obj);

    void onCarelissFailed(int code, String msg, Exception e);

    void onListSuccess(Object bean);

    void onListFailed(int code, String msg, Exception e);

    void onRefreshSuccess(Object bean);

    void onRefreshFailed(int code, String msg, Exception e);

    void onMoreSuccess(Object bean);

    void onMoreFailed(int code, String msg, Exception e);
}
