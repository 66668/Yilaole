package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnMineCollectEditListener;
import com.yilaole.modle.mine.MineCollectEditModleImpl;

/**
 * 我的收藏 编辑 p层
 */

public class MineCollectEditPresenterImpl implements OnMineCollectEditListener {
    OnMineCollectEditListener view;
    Context context;
    MineCollectEditModleImpl modle;

    public MineCollectEditPresenterImpl(Context context, OnMineCollectEditListener view) {
        this.view = view;
        this.context = context;
        modle = new MineCollectEditModleImpl();
    }


    /**
     * 列表
     *
     * @param token
     * @param total
     * @param pageSize
     */
    public void pCollectListData(String token, int total, int pageSize) {
        modle.mGetCollectData(token, total, pageSize, this);
    }

    public void pMoreData(String token, int total, int pageSize) {
        modle.mMoreData(token, total, pageSize, this);
    }

    public void pRefreshData(String token, int total, int pageSize) {
        modle.mRefreshData(token, total, pageSize, this);
    }

    /**
     * 取消关注
     */
    public void pUnCarePost(String token, int id) {
        modle.mUnCollect(id, token, this);
    }


    @Override
    public void onCarelessSuccess(Object obj) {
        view.onCarelessSuccess(obj);
    }

    @Override
    public void onCarelissFailed(int code, String msg, Exception e) {
        view.onCarelissFailed(code, msg, e);
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
