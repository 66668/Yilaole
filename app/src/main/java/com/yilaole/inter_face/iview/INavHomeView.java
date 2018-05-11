package com.yilaole.inter_face.iview;

import com.yilaole.bean.home.BannerBean;
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.bean.home.SearchBean;
import com.yilaole.bean.home.TextLooperBean;

import java.util.List;

/**
 * 资讯 接口
 * Created by sjy on 2017/8/31.
 */

public interface INavHomeView {

    //
    void onLooperImgSuccess(List<BannerBean> list);

    void onLooperImgFailed(int code,String msg, Exception e);

    //
    void onLooperSearchSuccess(List<SearchBean> list);

    void onLooperSearchFailed(int code,String msg, Exception e);

    //
    void onLooperTextSuccess(List<TextLooperBean> list);

    void onLooperTextFailed(int code,String msg, Exception e);

    //
    void onNewsSuccess(List<HomeNewsBean> list);

    void onNewsFailed(int code,String msg, Exception e);

    //
    void onTabSuccess(List<HomeTabBean> list);

    void onTabFailed(int code,String msg, Exception e);

}
