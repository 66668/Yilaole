package com.yilaole.inter_face.ilistener;

/**
 * 修改密码 接口回调
 */

public interface OnChangePsListener {

    void onChangePsSuccess(Object obj);

    void onChangePsFailed(int code,String msg, Exception e);
}
