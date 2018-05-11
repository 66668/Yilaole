package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.modle.institute.DetailCommentModleImpl;
import com.yilaole.utils.Utils;

/**
 * 用户点评
 */

public class DetailUserCommentPresenterImpl implements OnMoreAndRefreshListener {
    OnMoreAndRefreshListener view;
    Context context;
    DetailCommentModleImpl modle;
    String macId;

    public DetailUserCommentPresenterImpl(Context context, OnMoreAndRefreshListener view) {
        this.view = view;
        this.context = context;
        modle = new DetailCommentModleImpl();
        macId = Utils.getMacId(context);
    }

    public void pLoadData(int id, int all, int size) {
        modle.mLoadCommentData(id, all, size, macId, this);

    }

    public void pMoreData(int id, int all, int size) {
        modle.mMoreData(id, all, size, macId, this);
    }

    public void pRefreshData(int id, int all, int size) {
        modle.mRefreshData(id, all, size, macId, this);
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
