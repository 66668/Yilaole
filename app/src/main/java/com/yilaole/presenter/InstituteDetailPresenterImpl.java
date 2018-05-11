package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnInstituteDetailListener;
import com.yilaole.inter_face.ipresenter.IInstituteDetailPresenter;
import com.yilaole.modle.institute.InstituteDetailModleImpl;
import com.yilaole.utils.Utils;

/**
 * p层 机构详情
 */

public class InstituteDetailPresenterImpl implements IInstituteDetailPresenter, OnInstituteDetailListener {
    Context mContext;
    OnInstituteDetailListener instituteDetailView;
    InstituteDetailModleImpl modle;
    String macId;

    public InstituteDetailPresenterImpl(Context context, OnInstituteDetailListener view) {
        this.mContext = context;
        this.instituteDetailView = view;
        macId = Utils.getMacId(context);
        modle = new InstituteDetailModleImpl();
    }

    /**
     * ======================================================================
     * ================================复写interface======================================
     * ======================================================================
     */
    @Override
    public void pLoadBannergData(int id) {
        modle.mLoadBanner(id, this);
    }

    @Override
    public void pLoadDetailData(int id, String macID) {
        modle.mLoadDetailData(id, macID, this);
    }

    @Override
    public void pLoadCommentData(int id, int all, int pageSize) {
        modle.mLoadCommentData(id, all, pageSize, macId, this);
    }

    @Override
    public void pCollect(int id, String token) {
        modle.mCollect(id, token, this);
    }

    @Override
    public void pUnCollect(int id, String token) {
        modle.mUnCollect(id, token, this);
    }


    /**
     * ======================================================================
     * =================================接口回调=====================================
     * ======================================================================
     */
    //banner
    @Override
    public void onBannerSuccess(Object obj) {
        instituteDetailView.onBannerSuccess(obj);
    }

    @Override
    public void onBannerFailed(int code, String msg, Exception e) {
        instituteDetailView.onBannerFailed(code, msg, e);
    }

    //详情
    @Override
    public void onDetailSuccess(Object object) {
        instituteDetailView.onDetailSuccess(object);
    }

    @Override
    public void onDetailFailed(int code, String msg, Exception e) {
        instituteDetailView.onDetailFailed(code, msg, e);
    }

    //评论
    @Override
    public void onCommentSuccess(Object obj) {
        instituteDetailView.onCommentSuccess(obj);
    }

    @Override
    public void onCommentFailed(int code, String msg, Exception e) {
        instituteDetailView.onCommentFailed(code, msg, e);
    }

    //收藏
    @Override
    public void onCollectSuccess(Object object) {
        instituteDetailView.onCollectSuccess(object);
    }

    @Override
    public void onCollectFailed(int code, String msg, Exception e) {
        instituteDetailView.onCollectFailed(code, msg, e);
    }

    //取消收藏
    @Override
    public void onUnCollectSuccess(Object object) {
        instituteDetailView.onUnCollectSuccess(object);
    }


    @Override
    public void onUnCollectFailed(int code, String msg, Exception e) {
        instituteDetailView.onUnCollectFailed(code, msg, e);
    }
}
