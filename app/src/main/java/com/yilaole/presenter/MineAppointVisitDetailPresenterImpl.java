package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.mine.MineAppointVisitModleImpl;

/**
 * 预约参观 Detail
 */

public class MineAppointVisitDetailPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    MineAppointVisitModleImpl modle;

    public MineAppointVisitDetailPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new MineAppointVisitModleImpl();
    }

    public void pLoadDetailData(String token, int id) {
        modle.mLoadDetailData(token, id, this);
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
