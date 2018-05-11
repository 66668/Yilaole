package com.yilaole.inter_face.imodle;


import com.yilaole.inter_face.ilistener.OnNavHomeListener;

/**
 * 资讯获取数据接口.
 */

public interface INavHomeModle {

    void mLoadLooperImgData(OnNavHomeListener listener, String city);

    void mLoadSearchData(String city, OnNavHomeListener listener);

    void mLoadLooperTextData(String city, OnNavHomeListener listener);

    void mLoadHorizontalNewstData(OnNavHomeListener listener, String city);

    void mLoadHomeTabData(String city,OnNavHomeListener listener);
}
