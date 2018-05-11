package com.yilaole.modle.mine;

import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.mine.MineCommentItemBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 我的-我的评论
 */

public class MineCommentListModleImpl {

    public void mGetCommentList(String token, int totol, int size, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getMineCommentListData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MineCommentItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的评论--onError:" + e.toString());
                        listener.onListFailed(-1, "我的评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MineCommentItemBean> bean) {
                        MLog.d(TAG, "我的评论-onNext");

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

    public void mMoreData(String token, int totol, int size, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getMineCommentListData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MineCommentItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的评论--onError:" + e.toString());
                        listener.onMoreFailed(-1, "我的评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MineCommentItemBean> bean) {
                        MLog.d(TAG, "我的评论-onNext");

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

    public void mRefreshData(String token, int totol, int size, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getMineCommentListData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MineCommentItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的评论--onError:" + e.toString());
                        listener.onRefreshFailed(-1, "我的评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MineCommentItemBean> bean) {
                        MLog.d(TAG, "我的评论-onNext");

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
