package com.yilaole.inter_face.imodle;


import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.ilistener.OnNavNewsListener;

/**
 * 资讯获取数据接口.
 */

public interface INavNewsModle {
    void mLoadTabData(OnNavNewsListener listener);

    void mLoadLooperImgData(OnNavNewsListener listener, String city);

    void mLoadListData(OnMoreAndRefreshListener listener, int pageTotle, int pageSize, int type);

    void mMoreListData(OnMoreAndRefreshListener listener, int pageTotle, int pageSize, int type);

    void mRefreshListData(OnMoreAndRefreshListener listener, int pageTotle, int pageSize, int type);
}
