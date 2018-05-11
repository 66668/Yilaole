package com.yilaole.inter_face.imodle;


import com.yilaole.inter_face.ilistener.OnFilterConditionListener;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.ilistener.OnInstitutionAreaListener;

import java.util.Map;

/**
 * Created by sjy on 2017/8/31.
 */

public interface IInstitutionModle {

    void mLoadAreaData(OnInstitutionAreaListener listener);

    void mLoadFilterData(OnFilterConditionListener listener);

    void mLoadListData(Map<String, Object> map, OnMoreAndRefreshListener listener);

    void mRefreshListData(Map<String, Object> map, OnMoreAndRefreshListener listener);

    void mMoreListData(Map<String, Object> map, OnMoreAndRefreshListener listener);
}
