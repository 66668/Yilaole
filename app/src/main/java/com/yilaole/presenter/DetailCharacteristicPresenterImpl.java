package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailCharacteristicModleImpl;
import com.yilaole.utils.Utils;

/**
 * 医养特色
 */

public class DetailCharacteristicPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailCharacteristicModleImpl modle;
    String macid;

    public DetailCharacteristicPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        macid = Utils.getMacId(context);
        modle = new DetailCharacteristicModleImpl();
    }

    public void pLoadData(int id) {
        modle.mLoadData(id, macid, this);
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
