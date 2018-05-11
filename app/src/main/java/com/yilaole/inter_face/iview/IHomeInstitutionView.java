package com.yilaole.inter_face.iview;

import com.yilaole.bean.home.HomeInstituteItemBean;

import java.util.List;

/**
 * 首页——嵌套fragment的view层 接口
 * Created by sjy on 2017/8/31.
 */

public interface IHomeInstitutionView {

    void onTabDataSuccess(List<HomeInstituteItemBean> list);

    void onTabDataFailed(int code,String msg, Exception e);

    void onCollectionSuccess(Object obj);

    void onCollectionFailed(int code,String msg, Exception e);

}
