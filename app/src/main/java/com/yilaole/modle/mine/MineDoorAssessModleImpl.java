package com.yilaole.modle.mine;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.mine.DoorAssessDetailBean;
import com.yilaole.bean.mine.DoorAssessItemBean;
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
 * 我的- 上门评估
 */

public class MineDoorAssessModleImpl {


    public void mLoadDetailData(String token, int id, final OnCommonListener listener) {
        MLog.d("评估详情上传参数", "token=" + token + "\n"
                + "id=" + id);

        MyHttpService.Builder.getHttpServer().getDoorAssessDetailData(token, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<DoorAssessDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "上门评估--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "上门评估--onError:" + e.toString());
                        listener.onDataFailed(-1, "上门评估", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<DoorAssessDetailBean> bean) {
                        MLog.d(TAG, "上门评估-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDataSuccess(bean.getResult());
                        } else if (bean.getCode() == 404) {
                            //code = 0处理
                            listener.onDataFailed(bean.getCode(), "404", new Exception(bean.getMessage()));
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    public void mLoadListData(String token, int total, int pageSize, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getDoorAssessListData(token, total, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DoorAssessItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "上门评估--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "上门评估--onError:" + e.toString());
                        listener.onListFailed(-1, "上门评估", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DoorAssessItemBean> bean) {
                        MLog.d(TAG, "上门评估-onNext");

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

        MyHttpService.Builder.getHttpServer().getDoorAssessListData(token, total, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DoorAssessItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "上门评估--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "上门评估--onError:" + e.toString());
                        listener.onMoreFailed(-1, "上门评估", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DoorAssessItemBean> bean) {
                        MLog.d(TAG, "上门评估-onNext");

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

        MyHttpService.Builder.getHttpServer().getDoorAssessListData(token, total, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DoorAssessItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "上门评估--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "上门评估--onError:" + e.toString());
                        listener.onRefreshFailed(-1, "上门评估", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DoorAssessItemBean> bean) {
                        MLog.d(TAG, "上门评估-onNext");

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
