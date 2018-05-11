package com.yilaole.inter_face.idialog;

/**
 * 资讯详情 评论dialog的回调
 */

public interface INewsCommentDialogCallback {

    String getCommentText();

    void setCommentText(String commentTextTemp);

    //上传
    void postComment(String commentTextTemp);
}
