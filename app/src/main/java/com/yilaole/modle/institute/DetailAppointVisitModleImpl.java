package com.yilaole.modle.institute;

import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.mine.RegCodeBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnDetailAppointVisitListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 创建 预约参观
 */

public class DetailAppointVisitModleImpl {

    public void mPostData(int id,
                          String name,
                          String token,
                          String contact_name,
                          int number,
                          String elder_name,
                          String elder_age,
                          String contact_way,
                          String visit_time,
                          String code,
                          final OnDetailAppointVisitListener listener) {

        MyHttpService.Builder.getHttpServer().postAppointVisitData(id
                , name
                , token
                , contact_name
                , number
                , elder_name
                , elder_age
                , contact_way
                , visit_time
                , code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<String>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "预约参观--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "预约参观异常--onError:" + e.toString());
                        listener.onAppointVisitPostFailed(-1,"预约参观异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<String> bean) {
                        MLog.d(TAG, "预约参观-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onAppointVisitPostSuccess(bean.getResult());
                        }  else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onAppointVisitPostFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 获取验证码
     */
    public void mgetCode(String phoneNumber, final OnDetailAppointVisitListener listener) {

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
                        listener.onAppointVisitCodeFailed(-1,"获取验证码异常", (Exception) e);
                    }

                    @Override
                    public void onNext(RegCodeBean bean) {
                        MLog.d(TAG, "验证码(注册)-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onAppointVisitCodeSuccess("发送成功！");
                        }  else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onAppointVisitCodeFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

}
