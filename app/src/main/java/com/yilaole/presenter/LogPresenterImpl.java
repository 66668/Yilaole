package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnChangePsListener;
import com.yilaole.inter_face.ilistener.OnForgetAndChangePsListener;
import com.yilaole.inter_face.ilistener.OnForgetPostListener;
import com.yilaole.inter_face.ilistener.OnLoginListener;
import com.yilaole.inter_face.ilistener.OnRegListener;
import com.yilaole.inter_face.ipresenter.IChangePsPresenter;
import com.yilaole.inter_face.ipresenter.IForgetAndChangePsPresenter;
import com.yilaole.inter_face.ipresenter.ILoginPresenter;
import com.yilaole.inter_face.ipresenter.IRegPresenter;
import com.yilaole.modle.mine.LogRegChangModleImpl;

/**
 * 登录相关 p层实现
 */

public class LogPresenterImpl implements ILoginPresenter, IRegPresenter, IForgetAndChangePsPresenter, IChangePsPresenter
        , OnLoginListener, OnRegListener, OnForgetAndChangePsListener, OnForgetPostListener, OnChangePsListener {
    Context context;

    //登录
    OnLoginListener loginview;
    OnForgetAndChangePsListener forgetview;
    OnForgetPostListener forgetPostview;//
    OnChangePsListener changePsView;//
    LogRegChangModleImpl modle;

    //注册
    OnRegListener view;

    //注册 初始化
    public LogPresenterImpl(Context context, OnRegListener view) {
        this.context = context;
        this.view = view;
        modle = new LogRegChangModleImpl();
    }

    //登录 初始化
    public LogPresenterImpl(Context context, OnLoginListener view) {
        this.context = context;
        this.loginview = view;
        modle = new LogRegChangModleImpl();
    }

    //忘记密码 初始化
    public LogPresenterImpl(Context context, OnForgetAndChangePsListener view) {
        this.context = context;
        this.forgetview = view;
        modle = new LogRegChangModleImpl();
    }

    //忘记密码 提交 初始化
    public LogPresenterImpl(Context context, OnForgetPostListener view) {
        this.context = context;
        this.forgetPostview = view;
        modle = new LogRegChangModleImpl();
    }

    //修改密码 初始化
    public LogPresenterImpl(Context context, OnChangePsListener view) {
        this.context = context;
        this.changePsView = view;
        modle = new LogRegChangModleImpl();
    }

    /**
     * 登录相关
     * <p>
     * OnLoginListener
     */
    @Override
    public void pLoginByPhoneAndPs(String user_phone, String ps) {
        modle.mLoginByPhoneAndPs(this, user_phone, ps);
    }


    /**
     * 注册
     * <p>
     * OnRegListener
     *
     * @param user_phone
     * @param ps
     * @param code
     */
    @Override
    public void pRegist(String user_phone, String ps, String code) {
        modle.mRegister(this, user_phone, ps, code);
    }


    /**
     * token验证
     *
     * @param token
     */
    @Override
    public void pLoginToken(String token) {

    }

    /**
     * 验证码（注册）
     *
     * @param user_phone
     */

    @Override
    public void pGetCode(String user_phone) {
        modle.mGetRegCode(this, user_phone);
    }

    /**
     * 验证码（忘记密码的修改）
     * <p>
     * OnForgetAndChangePsListener
     *
     * @param user_phone
     */
    @Override
    public void pGetChangeAndForgetCode(String user_phone) {
        modle.mGetForgetAndChangeCode(this, user_phone);
    }

    /**
     * 忘记密码 提交
     * <p>
     * OnForgetPostListener
     *
     * @param user_phone
     */
    @Override
    public void pForgetAndChangePost(String user_phone, String ps, String code) {
        modle.mForgetPost(this, user_phone, ps, code);
    }

    /**
     * 修改密码 提交
     *
     * @param token
     * @param oldPs
     * @param newPs
     */
    @Override
    public void pChangePsPost(String token, String oldPs, String newPs) {
        modle.mChangePsPost(token, oldPs, newPs, this);
    }
    /**
     * ======================================================================================================
     * ====================================================返回View的回调==================================================
     * ======================================================================================================
     */
    /**
     * 登录
     *
     * @param obj
     */
    @Override
    public void onLogSuccess(Object obj) {
        loginview.onLogSuccess(obj);
    }

    @Override
    public void onLogFailed(int code, String msg, Exception e) {
        loginview.onLogFailed(code, msg, e);
    }

    /**
     * 验证码(注册)
     *
     * @param object
     */
    @Override
    public void onCodeSuccess(Object object) {
        view.onCodeSuccess(object);
    }

    @Override
    public void onCodeFailed(int code, String msg, Exception e) {
        view.onCodeFailed(code, msg, e);

    }

    /**
     * 注册
     *
     * @param obj
     */
    @Override
    public void onRegSuccess(Object obj) {
        view.onRegSuccess(obj);
    }

    @Override
    public void onRegFailed(int code, String msg, Exception e) {
        view.onRegFailed(code, msg, e);
    }

    /**
     * 验证码(忘记密码)
     *
     * @param obj
     */
    @Override
    public void onForgetCodeSuccess(Object obj) {
        forgetview.onForgetCodeSuccess(obj);
    }

    @Override
    public void onForgetCodeFailed(int code, String msg, Exception e) {
        forgetview.onForgetCodeFailed(code, msg, e);
    }

    /**
     * 忘记密码 提交
     *
     * @param obj
     */
    @Override
    public void onForgetAndChangePostSuccess(Object obj) {
        forgetPostview.onForgetAndChangePostSuccess(obj);
    }

    @Override
    public void onForgetAndChangePostFailed(int code, String msg, Exception e) {
        forgetPostview.onForgetAndChangePostFailed(code, msg, e);
    }

    //修改密码

    @Override
    public void onChangePsSuccess(Object obj) {
        changePsView.onChangePsSuccess(obj);
    }

    @Override
    public void onChangePsFailed(int code, String msg, Exception e) {
        changePsView.onChangePsFailed(code, msg, e);
    }
}
