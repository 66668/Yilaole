package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnDetailAppointVisitListener;
import com.yilaole.modle.institute.DetailAppointVisitModleImpl;

/**
 * 创建 预约参观
 */

public class DetailAppointViistPresenterImpl implements OnDetailAppointVisitListener {
    OnDetailAppointVisitListener view;
    Context context;
    DetailAppointVisitModleImpl modle;

    public DetailAppointViistPresenterImpl(Context context, OnDetailAppointVisitListener view) {
        this.view = view;
        this.context = context;
        modle = new DetailAppointVisitModleImpl();
    }

    public void pLoadData(int id,
                          String name,
                          String token,
                          String contact_name,
                          int number,
                          String elder_name,
                          String elder_age,
                          String contact_way,
                          String visit_time,
                          String code) {
        modle.mPostData(id
                , name
                , token
                , contact_name
                , number
                , elder_name
                , elder_age
                , contact_way
                , visit_time
                , code
                , this);
    }

    public void getCode(String phone) {
        modle.mgetCode(phone, this);
    }

    @Override
    public void onAppointVisitPostSuccess(Object obj) {
        view.onAppointVisitPostSuccess(obj);
    }

    @Override
    public void onAppointVisitPostFailed(int code,String msg, Exception e) {
        view.onAppointVisitPostFailed(code,msg, e);
    }

    @Override
    public void onAppointVisitCodeSuccess(Object obj) {
        view.onAppointVisitCodeSuccess(obj);
    }

    @Override
    public void onAppointVisitCodeFailed(int code,String msg, Exception e) {
        view.onAppointVisitCodeFailed(code,msg, e);
    }
}
