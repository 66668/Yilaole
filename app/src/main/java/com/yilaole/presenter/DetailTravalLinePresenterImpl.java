package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailTravalLineModleImpl;
import com.yilaole.utils.Utils;

/**
 * 乘车线路
 */

public class DetailTravalLinePresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailTravalLineModleImpl modle;

    public DetailTravalLinePresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new DetailTravalLineModleImpl();
    }

    public void pLoadData(int id) {
        String macId = Utils.getMacId(context);
        modle.mLoadData(id, macId,3,this);
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
