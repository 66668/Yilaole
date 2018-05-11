package com.yilaole.inter_face.ilistener;

/**
 * 修改密码 接口回调
 */

public interface OnChangePhotoListener {

    void onChangePhoteSuccess(Object obj);

    void onChangePhoteFailed(int code, String msg, Exception e);
}
