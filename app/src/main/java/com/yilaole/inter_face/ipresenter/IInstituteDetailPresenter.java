package com.yilaole.inter_face.ipresenter;

/**
 *
 */

public interface IInstituteDetailPresenter {

    /**
     * 轮播图数据
     */
    void pLoadBannergData(int id);

    /**
     * 获取详情
     */
    void pLoadDetailData(int id, String MACID);


    /**
     * 获取评论
     */
    void pLoadCommentData(int id, int all, int pageSize);

    /**
     * 收藏机构
     */
    void pCollect(int id, String token);

    /**
     * 取消收藏机构
     */
    void pUnCollect(int id, String token);


}
