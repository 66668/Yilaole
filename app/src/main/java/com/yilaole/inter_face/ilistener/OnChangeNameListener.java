package com.yilaole.inter_face.ilistener;

/**
 * 修改密码 接口回调
 */

public interface OnChangeNameListener {

    void onChangeNameSuccess(Object obj);

    void onChangeNameFailed(int code,String msg, Exception e);
}
