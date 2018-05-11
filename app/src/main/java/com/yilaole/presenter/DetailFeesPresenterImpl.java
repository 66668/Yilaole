package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailFeesModleImpl;
import com.yilaole.utils.Utils;

/**
 * 收费标准
 */

public class DetailFeesPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailFeesModleImpl modle;
    String macId;

    public DetailFeesPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        macId = Utils.getMacId(context);
        modle = new DetailFeesModleImpl();
    }

    public void pLoadData(int id) {
        modle.mLoadData(id, macId, this);
    }

    @Override
    public void onDataSuccess(Object obj) {
        view.onDataSuccess(obj);
    }

    @Override
    public void onDataFailed(int code, String msg, Exception e) {
        view.onDataFailed(code, msg, e);
    }
}
