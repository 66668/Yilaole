package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.bean.news.NewsBannerBean;
import com.yilaole.bean.news.NewsTabBean;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.ilistener.OnNavNewsListener;
import com.yilaole.inter_face.imodle.INavNewsModle;
import com.yilaole.inter_face.ipresenter.INavNewsPresenter;
import com.yilaole.inter_face.ipresenter.INewsPresenter;
import com.yilaole.inter_face.iview.INavNewsView;
import com.yilaole.modle.news.NewsModleImpl;
import com.yilaole.save.SPUtil;

import java.util.List;

/**
 * 资讯 p层实现
 */

public class NewsPresenterImpl implements INavNewsPresenter, INewsPresenter, OnNavNewsListener, OnMoreAndRefreshListener {
    Context context;
    INavNewsView view;
    OnMoreAndRefreshListener beanview;
    INavNewsModle modle;

    public NewsPresenterImpl(Context context, INavNewsView view) {
        this.context = context;
        this.view = view;
        modle = new NewsModleImpl();
    }

    public NewsPresenterImpl(Context context, OnMoreAndRefreshListener view) {
        this.context = context;
        this.beanview = view;
        modle = new NewsModleImpl();
    }

    //获取tab
    @Override
    public void pLoadTabData() {
        modle.mLoadTabData(this);
    }

    // 资讯轮播图
    @Override
    public void pLoadLooperImgData() {
        String city = SPUtil.getLocation();
        modle.mLoadLooperImgData(this, city);
    }

    //获取列表数据
    @Override
    public void pLoadListData(int totol, int size, int type) {

        modle.mLoadListData(this, totol, size, type);

    }

    /**
     * 加载更多
     *
     * @param totol
     * @param size
     * @param type
     */
    @Override
    public void pMoreListData(int totol, int size, int type) {
        modle.mMoreListData(this, totol, size, type);
    }

    /**
     * 刷新
     *
     * @param totol
     * @param size
     * @param type
     */
    @Override
    public void pRefreshListData(int totol, int size, int type) {
        modle.mRefreshListData(this, totol, size, type);
    }

    /**
     * =========================================================================================
     */
    //
    @Override
    public void onTabDataSuccess(List<NewsTabBean> List) {
        view.onTabDataSuccess(List);
    }

    @Override
    public void onTabDataFailed(int code, String msg, Exception e) {
        view.onTabDataFailed(code, msg, e);
    }

    //
    @Override
    public void onLooperImgSuccess(List<NewsBannerBean> list) {
        view.onLooperImgSuccess(list);
    }

    @Override
    public void onLooperImgFailed(int code, String msg, Exception e) {
        view.onLooperImgFailed(code, msg, e);
    }

    //
    @Override
    public void onListSuccess(Object bean) {
        beanview.onListSuccess(bean);
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
        beanview.onListFailed(code, msg, e);
    }

    //
    @Override
    public void onRefreshSuccess(Object bean) {
        beanview.onRefreshSuccess(bean);

    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {
        beanview.onRefreshFailed(code, msg, e);
    }

    //
    @Override
    public void onMoreSuccess(Object bean) {
        beanview.onMoreSuccess(bean);

    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {
        beanview.onMoreFailed(code, msg, e);
    }
}
