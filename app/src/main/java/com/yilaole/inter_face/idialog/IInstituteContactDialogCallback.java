package com.yilaole.inter_face.idialog;

/**
 * 机构详情 评论dialog的回调
 */

public interface IInstituteContactDialogCallback {

    String getContactNumber();

    void postNumber(String commentTextTemp);
}
