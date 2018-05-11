package com.yilaole.inter_face.ipresenter;

/**
 * Created by sjy on 2017/8/31.
 */

public interface INavHomePresenter {

    /**
     * 搜索轮播数据
     */
    void pLoadSearchData(String city);

    /**
     * 轮播图数据
     */
    void pLoadLooperImgData(String city);

    /**
     * 文字轮播数据
     */
    void pLoadLooperTextData(String city);

    /**
     * 首页资讯
     */
    void pLoadNewsData(String city);

    /**
     * 首页tab
     */
    void pLoadTabData(String city);

}
