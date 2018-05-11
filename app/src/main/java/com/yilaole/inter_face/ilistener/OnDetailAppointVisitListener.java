package com.yilaole.inter_face.ilistener;

/**
 * 机构详情 预约参观 回调
 */

public interface OnDetailAppointVisitListener {

    void onAppointVisitPostSuccess(Object obj);

    void onAppointVisitPostFailed(int code,String msg, Exception e);

    void onAppointVisitCodeSuccess(Object obj);

    void onAppointVisitCodeFailed(int code,String msg, Exception e);

}
