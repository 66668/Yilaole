package com.yilaole.presenter;

import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.SearchHistoryActivityModleImpl;

/**
 *
 */

public class SearchHistoryActivityPresenterImpl implements OnCommonListener {
    OnCommonListener view;
    SearchHistoryActivityModleImpl modle;

    public SearchHistoryActivityPresenterImpl(OnCommonListener view) {
        this.view = view;
        modle = new SearchHistoryActivityModleImpl();
    }

    public void getHotData() {
        modle.mLoadHotData(this);

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
