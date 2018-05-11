package com.yilaole.inter_face.ilistener;

import com.yilaole.bean.news.NewsBannerBean;
import com.yilaole.bean.news.NewsTabBean;

import java.util.List;

/**
 * 获取资讯 监听
 */

public interface OnNavNewsListener {

    void onTabDataSuccess(List<NewsTabBean> List);

    void onTabDataFailed(int code,String msg, Exception e);

    void onLooperImgSuccess(List<NewsBannerBean> list);

    void onLooperImgFailed(int code,String msg, Exception e);
}
