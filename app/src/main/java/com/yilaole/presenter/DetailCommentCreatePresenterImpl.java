package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.bean.institution.detail.PostCommentBean;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.modle.institute.DetailCommentModleImpl;

/**
 * 机构点评 创建
 */

public class DetailCommentCreatePresenterImpl implements OnCommonListener {
    OnCommonListener view;
    Context context;
    DetailCommentModleImpl modle;

    public DetailCommentCreatePresenterImpl(Context context, OnCommonListener view) {
        this.view = view;
        this.context = context;
        modle = new DetailCommentModleImpl();
    }

    /**
     * 评论上传
     *
     * @param bean
     */
    public void pPostData(PostCommentBean bean) {


        if (bean.getFile0() == null && bean.getFile1() == null && bean.getFile2() == null) {//无图上传
            modle.mPostDataWithoutPic(bean.getId()
                    , bean.getOrder_id()
                    , bean.getToken()
                    , bean.getAgency_score()
                    , bean.getServoce_score()
                    , bean.getContent()
                    , bean.getType(),this);
        } else {//有图上传
            modle.mPostDataWithPic(bean.getId()
                    , bean.getOrder_id()
                    , bean.getToken()
                    , bean.getAgency_score()
                    , bean.getServoce_score()
                    , bean.getContent()
                    , bean.getType()
                    , bean.getFile0()
                    , bean.getFile1()
                    , bean.getFile2()
                    , this);
        }


    }

    @Override
    public void onDataSuccess(Object obj) {
        view.onDataSuccess(obj);
    }

    @Override
    public void onDataFailed(int code, String msg, Exception e) {
        view.onDataFailed(code,msg, e);
    }
}
