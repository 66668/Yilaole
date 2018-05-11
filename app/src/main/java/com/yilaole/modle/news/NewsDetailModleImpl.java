package com.yilaole.modle.news;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.news.HotNewsBean;
import com.yilaole.bean.news.NewsCommentBean;
import com.yilaole.bean.news.NewsDetailBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnNewsDetailListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 资讯详情 m层
 */

public class NewsDetailModleImpl {

    /**
     * 资讯详情
     */
    public void mLoadDetailData(int id, String token, final OnNewsDetailListener listener) {

        MyHttpService.Builder.getHttpServer().getNewsDetailData(id, token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<NewsDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "咨询详情--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "咨询详情--onError:" + e.toString());
                        listener.onDetailFailed(-1, "咨询详情异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<NewsDetailBean> bean) {
                        MLog.d(TAG, "咨询详情-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDetailSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDetailFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 热门文章
     */
    public void mLoadHotNewsData(final OnNewsDetailListener listener) {

        MyHttpService.Builder.getHttpServer().getHotNewsData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<HotNewsBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "热门文章--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "热门文章--onError:" + e.toString());
                        listener.onHotNewsFailed(-1, "热门文章异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<HotNewsBean> bean) {
                        MLog.d(TAG, "热门文章-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            MLog.d("热门文章：" + bean.getResult().size());
                            listener.onHotNewsSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onHotNewsFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 评论列表
     */
    public void mLoadNewsCommentData(int id, final OnNewsDetailListener listener) {

        MyHttpService.Builder.getHttpServer().getCommentListData(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<NewsCommentBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "文章评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "文章评论--onError:" + e.toString());
                        listener.onNewsCommentFailed(-1, "文章评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<NewsCommentBean> bean) {
                        MLog.d(TAG, "文章评论-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            MLog.d("文章评论：" + bean.getResult().size());
                            listener.onNewsCommentSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onNewsCommentFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 发送评论
     */
    public void mSendNewsCommentData(int id, String token, String cotent, int cid, final OnNewsDetailListener listener) {

        MyHttpService.Builder.getHttpServer().postNewsComment(id, token, cotent, cid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "发送评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "发送评论--onError:" + e.toString());
                        listener.onSendFailed(-1, "发送评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        MLog.d(TAG, "发送评论-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onSendSuccess("发送成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onSendFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 赞
     */
    public void mSupprotNewsData(int id, String token, final OnNewsDetailListener listener) {

        MyHttpService.Builder.getHttpServer().postNewsSupport(id, token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "赞--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "赞--onError:" + e.toString());
                        listener.onSupportFailed(-1, "赞异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        MLog.d(TAG, "赞-onNext");
                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onSupportSuccess("点赞成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onSupportFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
