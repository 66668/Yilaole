package com.yilaole.presenter;

import com.yilaole.inter_face.ilistener.OnNewsDetailListener;
import com.yilaole.modle.news.NewsDetailModleImpl;

/**
 * 详情相关 p层
 */

public class NewsDetailPresenterImpl implements OnNewsDetailListener {
    private OnNewsDetailListener view;
    private NewsDetailModleImpl modle;

    public NewsDetailPresenterImpl(OnNewsDetailListener view) {
        this.view = view;
        modle = new NewsDetailModleImpl();
    }

    /**
     * =================================================================================================
     * =================================================================================================
     * =================================================================================================
     */
    /**
     * 详细信息
     *
     * @param id
     * @param token
     */
    public void pGetDetailData(int id, String token) {
        modle.mLoadDetailData(id, token, this);
    }

    /**
     * 热门文章
     */
    public void pGetHotNewsData() {
        modle.mLoadHotNewsData(this);
    }

    /**
     * 获取评论
     *
     * @param id
     */
    public void pGetNewsCommentData(int id) {
        modle.mLoadNewsCommentData(id, this);

    }

    /**
     * 发布 评论
     */
    public void pSendDetailCommnetData(int id, String token, String cotent, int cid) {
        modle.mSendNewsCommentData(id, token, cotent, cid, this);
    }

    /**
     * 点赞
     */
    public void pSupprotNewsData(int id, String token) {
        modle.mSupprotNewsData(id, token, this);
    }

    /**
     * =================================================================================================
     * =================================================================================================
     * =================================================================================================
     */
    //详情
    @Override
    public void onDetailSuccess(Object obj) {
        view.onDetailSuccess(obj);
    }

    @Override
    public void onDetailFailed(int code, String msg, Exception e) {
        view.onDetailFailed(code, msg, e);
    }

    //热门文章
    @Override
    public void onHotNewsSuccess(Object obj) {
        view.onHotNewsSuccess(obj);
    }

    @Override
    public void onHotNewsFailed(int code, String msg, Exception e) {
        view.onHotNewsFailed(code, msg, e);
    }

    //评论列表
    @Override
    public void onNewsCommentSuccess(Object obj) {
        view.onNewsCommentSuccess(obj);
    }

    @Override
    public void onNewsCommentFailed(int code, String msg, Exception e) {
        view.onNewsCommentFailed(code, msg, e);
    }

    //发布评论
    @Override
    public void onSendSuccess(Object obj) {
        view.onSendSuccess(obj);
    }

    @Override
    public void onSendFailed(int code, String msg, Exception e) {
        view.onSendFailed(code, msg, e);
    }

    //赞
    @Override
    public void onSupportSuccess(Object obj) {
        view.onSupportSuccess(obj);
    }

    @Override
    public void onSupportFailed(int code, String msg, Exception e) {
        view.onSupportFailed(code, msg, e);

    }
}
