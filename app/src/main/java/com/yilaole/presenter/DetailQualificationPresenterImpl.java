package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailQualificationModleImpl;
import com.yilaole.utils.Utils;

/**
 * 资质机构
 */

public class DetailQualificationPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailQualificationModleImpl modle;
    String macId;

    public DetailQualificationPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        macId = Utils.getMacId(context);
        modle = new DetailQualificationModleImpl();
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
