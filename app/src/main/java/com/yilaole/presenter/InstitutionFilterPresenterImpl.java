package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnFilterConditionListener;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.imodle.IInstitutionModle;
import com.yilaole.inter_face.ipresenter.IInstitutionFilterPresenter;
import com.yilaole.inter_face.iview.IInstitutionView;
import com.yilaole.modle.institute.InstitutionModleImpl;

import java.util.Map;

/**
 * 机构筛选p层实现
 */

public class InstitutionFilterPresenterImpl implements IInstitutionFilterPresenter, OnFilterConditionListener, OnMoreAndRefreshListener {
    Context context;
    IInstitutionView view;
    IInstitutionModle modle;

    public InstitutionFilterPresenterImpl(Context context, IInstitutionView view) {
        this.context = context;
        this.view = view;
        modle = new InstitutionModleImpl();
    }

    @Override
    public void pLoadFilterData() {
        modle.mLoadFilterData(this);
    }

    /**
     * 初始加载
     *
     * @param map
     */
    @Override
    public void pLoadListData(Map<String, Object> map) {
        modle.mLoadListData(map, this);
    }

    /**
     * 刷新
     *
     * @param map
     */
    @Override
    public void prefreshListData(Map<String, Object> map) {
        modle.mRefreshListData(map, this);
    }

    /**
     * 加载
     *
     * @param map
     */
    @Override
    public void pMoreListData(Map<String, Object> map) {
        modle.mMoreListData(map, this);
    }

    //多条件筛选
    @Override
    public void onFilterConditionSuccess(Object object) {
        view.onFilterConditionSuccess(object);
    }

    @Override
    public void onFilterConditionFailed(int code, String msg, Exception e) {
        view.onFilterConditionFailed(code, msg, e);
    }

    //
    @Override
    public void onListSuccess(Object bean) {
        view.onListSuccess(bean);
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
        view.onListFailed(code, msg, e);
    }

    //
    @Override
    public void onRefreshSuccess(Object bean) {
        view.onRefreshSuccess(bean);
    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {
        view.onRefreshFailed(code, msg, e);
    }

    //
    @Override
    public void onMoreSuccess(Object bean) {
        view.onMoreSuccess(bean);
    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {
        view.onMoreFailed(code, msg, e);
    }

}
