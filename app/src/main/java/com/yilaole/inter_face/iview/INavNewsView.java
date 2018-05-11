package com.yilaole.inter_face.iview;

import com.yilaole.bean.news.NewsBannerBean;
import com.yilaole.bean.news.NewsTabBean;

import java.util.List;

/**
 * 资讯 接口
 * Created by sjy on 2017/8/31.
 */

public interface INavNewsView {

    void onTabDataSuccess(List<NewsTabBean> List);

    void onTabDataFailed(int code,String msg, Exception e);

    void onLooperImgSuccess(List<NewsBannerBean> list);

    void onLooperImgFailed(int code,String msg, Exception e);
}
