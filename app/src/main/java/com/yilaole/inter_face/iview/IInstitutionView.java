package com.yilaole.inter_face.iview;

/**
 * 机构筛选 接口
 * Created by sjy on 2017/8/31.
 */

public interface IInstitutionView {

    void onFilterConditionSuccess(Object object);

    void onFilterConditionFailed(int code,String msg, Exception e);

    void onListSuccess(Object bean);

    void onListFailed(int code, String msg, Exception e);

    void onRefreshSuccess(Object bean);

    void onRefreshFailed(int code, String msg, Exception e);

    void onMoreSuccess(Object bean);

    void onMoreFailed(int code, String msg, Exception e);
}
