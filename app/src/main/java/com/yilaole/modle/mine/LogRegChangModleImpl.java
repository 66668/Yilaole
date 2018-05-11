package com.yilaole.modle.mine;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.mine.LoginBean;
import com.yilaole.bean.mine.RegCodeBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnChangePsListener;
import com.yilaole.inter_face.ilistener.OnForgetAndChangePsListener;
import com.yilaole.inter_face.ilistener.OnForgetPostListener;
import com.yilaole.inter_face.ilistener.OnLoginListener;
import com.yilaole.inter_face.ilistener.OnRegListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录相关 m层实现
 */

public class LogRegChangModleImpl {
    public static final String TAG = "SJY";

    /**
     * 登录
     *
     * @param listener
     */
    public void mLoginByPhoneAndPs(final OnLoginListener listener, String userPhone, String ps) {
        MLog.d("登录参数：", "user_phone=" + userPhone, "ps=" + ps);

        MyHttpService.Builder.getHttpServer().postLogin(userPhone, ps)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<LoginBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "登录--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "登录--登录失败异常onError:" + e.toString());
                        listener.onLogFailed(-1, "登录失败异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<LoginBean> bean) {
                        MLog.d(TAG, "登录-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onLogSuccess(bean.getResult());
                        } else if (bean.getCode() == 500) {
                            listener.onLogFailed(bean.getCode(), bean.getMessage(), new Exception("登录失败！"));
                        } else if (bean.getCode() == 400) {
                            listener.onLogFailed(bean.getCode(), bean.getMessage(), new Exception("登录失败！"));
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onLogFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 注册
     *
     * @param listener
     */
    public void mRegister(final OnRegListener listener, String user_phone, String ps, String code) {
        MLog.d("登录参数：", "user_phone=" + user_phone, "ps=" + ps);

        MyHttpService.Builder.getHttpServer().postRegist(user_phone, ps, code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "NewsModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--注册异常onError:" + e.toString());
                        listener.onRegFailed(-1, "注册异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        MLog.d(TAG, "NewsModleImpl-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onRegSuccess("注册成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onRegFailed(bean.getCode(), bean.getMessage(), new Exception("注册失败！"));
                        }
                    }

                });
    }

    /**
     * 验证码(注册)
     *
     * @param listener
     */
    public void mGetRegCode(final OnRegListener listener, String phoneNumber) {

        MyHttpService.Builder.getHttpServer().getRegCode(phoneNumber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegCodeBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "验证码(注册)--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "验证码(注册)--获取验证码异常onError:" + e.toString());
                        listener.onCodeFailed(-1, "获取验证码异常", (Exception) e);
                    }

                    @Override
                    public void onNext(RegCodeBean bean) {
                        MLog.d(TAG, "验证码(注册)-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onCodeSuccess("发送成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onCodeFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 验证码(忘记密码)
     *
     * @param listener
     */
    public void mGetForgetAndChangeCode(final OnForgetAndChangePsListener listener, String phoneNumber) {

        MyHttpService.Builder.getHttpServer().getChangeAndForgetCode(phoneNumber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegCodeBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "验证码(忘记密码+修改)--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--获取验证码异常onError:" + e.toString());
                        listener.onForgetCodeFailed(-1, "获取验证码异常", (Exception) e);
                    }

                    @Override
                    public void onNext(RegCodeBean bean) {
                        MLog.d(TAG, "验证码(忘记密码+修改)-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onForgetCodeSuccess("发送成功！");
                        } else if (bean.getCode() == 500) {
                            listener.onForgetCodeFailed(bean.getCode(), bean.getMessage(), new Exception("获取验证码失败！"));
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onForgetCodeFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 忘记密码的修改密码 提交
     *
     * @param listener
     */
    public void mForgetPost(final OnForgetPostListener listener, String user_phone, String ps, String code) {

        MyHttpService.Builder.getHttpServer().changeAndForgetPs(user_phone, ps, code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "忘记+修改密码 提交--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "忘记+修改密码 提交异常onError:" + e.toString());
                        listener.onForgetAndChangePostFailed(-1, "忘记+修改密码 提交异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        MLog.d(TAG, "忘记+修改密码 提交-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onForgetAndChangePostSuccess("注册成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onForgetAndChangePostFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 修改密码 提交
     */
    public void mChangePsPost(String token, String oldps, String newps, final OnChangePsListener listener) {

        MyHttpService.Builder.getHttpServer().changePsPost(token, oldps, newps)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "修改密码 提交--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "修改密码 提交异常onError:" + e.toString());
                        listener.onChangePsFailed(-1, "修改密码 提交异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        MLog.d(TAG, "修改密码 提交-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onChangePsSuccess("修改成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onChangePsFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
