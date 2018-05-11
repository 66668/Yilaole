package com.yilaole.inter_face.ilistener;

/**
 * 资讯详情 接口回调
 */

public interface OnNewsDetailListener {

    void onDetailSuccess(Object obj);

    void onDetailFailed(int code, String msg, Exception e);


    void onHotNewsSuccess(Object obj);

    void onHotNewsFailed(int code, String msg, Exception e);

    void onNewsCommentSuccess(Object obj);

    void onNewsCommentFailed(int code, String msg, Exception e);

    void onSendSuccess(Object obj);

    void onSendFailed(int code, String msg, Exception e);

    void onSupportSuccess(Object obj);

    void onSupportFailed(int code, String msg, Exception e);

}
