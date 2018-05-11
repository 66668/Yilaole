package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailDoorAssessModleImpl;

/**
 * 上门评估  创建
 */

public class DetailDoorAssessPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailDoorAssessModleImpl modle;

    public DetailDoorAssessPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new DetailDoorAssessModleImpl();
    }

    public void pLoadData(int id,
                          String name,
                          String token,
                          String contact_name,
                          String contact_way,
                          String address,
                          int elder_id,
                          String visit_time,
                          String remark) {
        modle.mPostData(id
                , name
                , token
                , contact_name
                , contact_way
                , address
                , elder_id
                , visit_time
                , remark
                , this);
    }

    @Override
    public void onDataSuccess(Object obj) {
        view.onDataSuccess(obj);
    }

    @Override
    public void onDataFailed(int code, String msg, Exception e) {
        view.onDataFailed(code,msg, e);
    }
}
