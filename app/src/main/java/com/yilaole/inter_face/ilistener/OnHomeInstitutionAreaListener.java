package com.yilaole.inter_face.ilistener;

import com.yilaole.bean.home.HomeInstituteItemBean;

import java.util.List;

/**
 * 获取机构筛选区域监听
 */

public interface OnHomeInstitutionAreaListener {

    void onTabDataSuccess(List<HomeInstituteItemBean> List);

    void onTabDataFailed(int code, String msg, Exception e);
}
