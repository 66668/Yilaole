package com.yilaole.inter_face.ilistener;

/**
 * 机构详情回调监听
 */

public interface OnInstituteDetailListener {
    //banner
    void onBannerSuccess(Object obj);

    void onBannerFailed(int code,String msg, Exception e);

    void onDetailSuccess(Object object);

    void onDetailFailed(int code,String msg, Exception e);

    void onCommentSuccess(Object obj);

    void onCommentFailed(int code,String msg, Exception e);

    void onCollectSuccess(Object object);

    void onCollectFailed(int code,String msg, Exception e);

    void onUnCollectSuccess(Object object);

    void onUnCollectFailed(int code,String msg, Exception e);


}
