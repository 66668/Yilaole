package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.mine.MineDoorAssessModleImpl;

/**
 * 上门评估 list
 */

public class MineDoorAssessDetailPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    MineDoorAssessModleImpl modle;

    public MineDoorAssessDetailPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new MineDoorAssessModleImpl();
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
