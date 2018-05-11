package com.yilaole.modle.mine;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.mine.MineCollectInstituteBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnMineCollectEditListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 我的-我的收藏 编辑
 */

public class MineCollectEditModleImpl {
    /**
     * 取消收藏
     */
    public void mUnCollect(int id, String token, final OnMineCollectEditListener listener) {

        MyHttpService.Builder.getHttpServer().unCollectPost(id, token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<String>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "取消收藏--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "取消收藏--onError:" + e.toString());
                        listener.onCarelissFailed(-1,"取消收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<String> bean) {
                        MLog.d(TAG, "取消收藏-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onCarelessSuccess(bean.getResult());
                        }  else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onCarelissFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    public void mGetCollectData(String token, int totol, int size, final OnMineCollectEditListener listener) {

        MyHttpService.Builder.getHttpServer().getMineCollectData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MineCollectInstituteBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的收藏--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的收藏--onError:" + e.toString());
                        listener.onListFailed(-1, "我的收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MineCollectInstituteBean> bean) {
                        MLog.d(TAG, "我的收藏-onNext");

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

    public void mRefreshData(String token, int totol, int size, final OnMineCollectEditListener listener) {

        MyHttpService.Builder.getHttpServer().getMineCollectData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MineCollectInstituteBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的收藏--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的收藏--onError:" + e.toString());
                        listener.onRefreshFailed(-1, "我的收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MineCollectInstituteBean> bean) {
                        MLog.d(TAG, "我的收藏-onNext");

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

    public void mMoreData(String token, int totol, int size, final OnMineCollectEditListener listener) {

        MyHttpService.Builder.getHttpServer().getMineCollectData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MineCollectInstituteBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的收藏--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的收藏--onError:" + e.toString());
                        listener.onMoreFailed(-1, "我的收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MineCollectInstituteBean> bean) {
                        MLog.d(TAG, "我的收藏-onNext");

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
}
