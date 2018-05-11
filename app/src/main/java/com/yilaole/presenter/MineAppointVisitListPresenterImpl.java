package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.modle.mine.MineAppointVisitModleImpl;

/**
 * 预约参观 list
 */

public class MineAppointVisitListPresenterImpl implements OnMoreAndRefreshListener {
    OnMoreAndRefreshListener view;
    Context context;
    MineAppointVisitModleImpl modle;

    public MineAppointVisitListPresenterImpl(Context context, OnMoreAndRefreshListener view) {
        this.view = view;
        this.context = context;
        modle = new MineAppointVisitModleImpl();
    }

    public void pLoadData(String token, int total, int pageSize) {
        modle.mLoadListData(token, total, pageSize, this);
    }

    public void pMoreData(String token, int total, int pageSize) {
        modle.mMoreData(token, total, pageSize, this);
    }

    public void pRefreshData(String token, int total, int pageSize) {
        modle.mRefreshData(token, total, pageSize, this);
    }

    @Override
    public void onListSuccess(Object bean) {
        view.onListSuccess(bean);
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
        view.onListFailed(code, msg, e);
    }

    @Override
    public void onRefreshSuccess(Object bean) {
        view.onRefreshSuccess(bean);
    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {
        view.onRefreshFailed(code, msg, e);
    }

    @Override
    public void onMoreSuccess(Object bean) {
        view.onMoreSuccess(bean);
    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {
        view.onMoreFailed(code, msg, e);
    }
}
