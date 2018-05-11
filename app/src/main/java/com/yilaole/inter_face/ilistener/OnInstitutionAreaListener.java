package com.yilaole.inter_face.ilistener;

import com.yilaole.bean.institution.filter.ProvinceBean;

import java.util.List;

/**
 * 获取机构筛选区域监听
 */

public interface OnInstitutionAreaListener {
    void onFilterAreraSuccess(List<ProvinceBean> provinceBeanList);

    void onFilterAreraFailed(int code,String msg, Exception e);
}
