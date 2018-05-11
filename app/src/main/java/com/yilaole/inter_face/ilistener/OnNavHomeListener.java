package com.yilaole.inter_face.ilistener;

import com.yilaole.bean.home.BannerBean;
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.bean.home.SearchBean;
import com.yilaole.bean.home.TextLooperBean;

import java.util.List;

/**
 * 获取资讯 监听
 */

public interface OnNavHomeListener {

    //轮播图
    void onLooperImgSuccess(List<BannerBean> list);

    void onLooperImgFailed(int code,String msg, Exception e);

    //搜索轮播
    void onLooperSearchSuccess(List<SearchBean> list);

    void onLooperSearchFailed(int code,String msg, Exception e);

    //文字轮播
    void onLooperTextSuccess(List<TextLooperBean> list);

    void onLooperTextFailed(int code,String msg, Exception e);

    //资讯
    void onNewsSuccess(List<HomeNewsBean> list);

    void onNewsFailed(int code,String msg, Exception e);

    //tab数据
    void onHomeTabSuccess(List<HomeTabBean> list);

    void onHomeTabFailed(int code,String msg, Exception e);
}
