package com.yilaole.inter_face.ilistener;

/**
 * 单一接口act的统一数据调用监听
 */

public interface OnCommonListener {

    void onDataSuccess(Object obj);

    void onDataFailed(int code, String msg, Exception e);
}
