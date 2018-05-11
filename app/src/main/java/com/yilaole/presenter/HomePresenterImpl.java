package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.bean.home.BannerBean;
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.bean.home.SearchBean;
import com.yilaole.bean.home.TextLooperBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnNavHomeListener;
import com.yilaole.inter_face.imodle.INavHomeModle;
import com.yilaole.inter_face.ipresenter.INavHomePresenter;
import com.yilaole.inter_face.iview.INavHomeView;
import com.yilaole.modle.home.HomeModleImpl;

import java.util.List;

/**
 * 资讯 p层实现
 */

public class HomePresenterImpl implements INavHomePresenter, OnNavHomeListener {
    Context context;
    INavHomeView view;
    INavHomeModle modle;
    ACache aCache;

    public HomePresenterImpl(Context context, INavHomeView view) {
        this.context = context;
        this.view = view;
        modle = new HomeModleImpl();
        aCache = ACache.get(context);
    }

    /**
     * 1
     */
    @Override
    public void pLoadSearchData(String city) {
        modle.mLoadSearchData(city, this);
    }

    /**
     * 2
     */
    @Override
    public void pLoadLooperImgData(String city) {

        modle.mLoadLooperImgData(this, city);
    }

    /**
     * 3
     */
    @Override
    public void pLoadLooperTextData(String city) {
        modle.mLoadLooperTextData(city, this);
    }

    /**
     * 4
     */
    @Override
    public void pLoadNewsData(String city) {
        modle.mLoadHorizontalNewstData(this, city);
    }

    @Override
    public void pLoadTabData(String city) {
        modle.mLoadHomeTabData(city,this);
    }


    //轮播图回调
    @Override
    public void onLooperImgSuccess(List<BannerBean> list) {
        view.onLooperImgSuccess(list);
    }

    @Override
    public void onLooperImgFailed(int code, String msg, Exception e) {
        view.onLooperImgFailed(code,msg, e);
    }

    //搜索轮播回调
    @Override
    public void onLooperSearchSuccess(List<SearchBean> list) {
        view.onLooperSearchSuccess(list);
    }

    @Override
    public void onLooperSearchFailed(int code, String msg, Exception e) {
        view.onLooperSearchFailed(code,msg, e);

    }

    //文字轮播回调
    @Override
    public void onLooperTextSuccess(List<TextLooperBean> list) {
        view.onLooperTextSuccess(list);
    }

    @Override
    public void onLooperTextFailed(int code, String msg, Exception e) {
        view.onLooperTextFailed(code,msg, e);

    }

    //首页资讯回调
    @Override
    public void onNewsSuccess(List<HomeNewsBean> list) {
        view.onNewsSuccess(list);

    }

    @Override
    public void onNewsFailed(int code, String msg, Exception e) {
        view.onNewsFailed(code,msg, e);

    }

    //首页tab
    @Override
    public void onHomeTabSuccess(List<HomeTabBean> list) {
        view.onTabSuccess(list);
    }

    @Override
    public void onHomeTabFailed(int code, String msg, Exception e) {
        view.onTabFailed(code,msg, e);
    }
}
