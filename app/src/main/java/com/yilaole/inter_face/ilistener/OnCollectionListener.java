package com.yilaole.inter_face.ilistener;

/**
 * 收藏统一调用监听
 */

public interface OnCollectionListener {

    void onCollectionSuccess(Object obj);

    void onCollectionFailed(int code,String msg, Exception e);
}
