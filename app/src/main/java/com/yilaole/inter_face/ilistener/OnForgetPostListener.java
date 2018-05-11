package com.yilaole.inter_face.ilistener;

/**
 * 忘记密码修改监听
 */

public interface OnForgetPostListener {

    void onForgetAndChangePostSuccess(Object obj);

    void onForgetAndChangePostFailed(int code,String msg, Exception e);

}
