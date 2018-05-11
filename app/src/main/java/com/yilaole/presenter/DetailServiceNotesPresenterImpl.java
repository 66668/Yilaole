package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailServiceNotesModleImpl;
import com.yilaole.utils.Utils;

/**
 * 服务须知
 */

public class DetailServiceNotesPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailServiceNotesModleImpl modle;

    public DetailServiceNotesPresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new DetailServiceNotesModleImpl();
    }

    public void pLoadData(int id) {
        String macId = Utils.getMacId(context);
        modle.mLoadData(id, macId, 3, this);
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
