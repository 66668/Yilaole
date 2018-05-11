package com.yilaole.inter_face.ilistener;

/**
 * 获取机构筛选 条件监听
 */

public interface OnFilterConditionListener {
    void onFilterConditionSuccess(Object bean);

    void onFilterConditionFailed(int code,String msg, Exception e);
}
