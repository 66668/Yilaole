package com.yilaole.modle.mine;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.mine.AppointVisitDetailBean;
import com.yilaole.bean.mine.AppointVisitItemBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 我的-预约参观
 */

public class MineAppointVisitModleImpl {


    public void mLoadDetailData(String token, int id, final OnCommonListener listener) {

        MyHttpService.Builder.getHttpServer().getAppoinDetailData(token, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<AppointVisitDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "预约参观--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "预约参观异常--onError:" + e.toString());
                        listener.onDataFailed(-1, "预约参观异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<AppointVisitDetailBean> bean) {
                        MLog.d(TAG, "预约参观-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onDataSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     */
    public void mLoadListData(String token, int total, int pageSize, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getAppointListData(token, total, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<AppointVisitItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "预约参观--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "预约参观--onError:" + e.toString());
                        listener.onListFailed(-1, "预约参观异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<AppointVisitItemBean> bean) {
                        MLog.d(TAG, "预约参观-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onListSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onListFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    public void mMoreData(String token, int total, int pageSize, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getAppointListData(token, total, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<AppointVisitItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "预约参观--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "预约参观--onError:" + e.toString());
                        listener.onMoreFailed(-1, "预约参观异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<AppointVisitItemBean> bean) {
                        MLog.d(TAG, "预约参观-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onMoreSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onMoreFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    public void mRefreshData(String token, int total, int pageSize, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getAppointListData(token, total, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<AppointVisitItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "预约参观--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "预约参观--onError:" + e.toString());
                        listener.onRefreshFailed(-1, "预约参观异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<AppointVisitItemBean> bean) {
                        MLog.d(TAG, "预约参观-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onRefreshSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onRefreshFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
