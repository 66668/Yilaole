package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.mine.MineMessageModleImpl;

/**
 * 我的评论 list
 */

public class MineMessagePresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    MineMessageModleImpl modle;

    public MineMessagePresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new MineMessageModleImpl();
    }

    public void pLoadData(String token, int total, int pageSize) {
        modle.mGetMessageData(token, total, pageSize, this);
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
