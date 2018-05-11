package com.yilaole.modle.institute;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.institution.detail.DetailBannerBean;
import com.yilaole.bean.institution.detail.DetailCommentBean;
import com.yilaole.bean.institution.detail.InstituteDetailBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnInstituteDetailListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 机构详情 m层
 */

public class InstituteDetailModleImpl {

    /**
     * 获取详情banner
     *
     * @param id
     * @param listener
     */
    public void mLoadBanner(int id, final OnInstituteDetailListener listener) {

        MyHttpService.Builder.getHttpServer().getDetailBanner(id, 2)//android设备传2
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DetailBannerBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "获取详情banner--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "获取详情banner--onError:" + e.toString());
                        listener.onBannerFailed(-1, "获取详情banner异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailBannerBean> bean) {
                        MLog.d(TAG, "获取详情banner-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onBannerSuccess(bean.getResult());
                        } else {
                            //code = 0处理
                            listener.onBannerFailed(bean.getCode(), bean.getMessage(), new Exception("获取失败！"));
                        }
                    }

                });
    }

    /**
     * 获取详情
     *
     * @param id
     * @param macId
     * @param listener
     */
    public void mLoadDetailData(int id, String macId, final OnInstituteDetailListener listener) {


        MyHttpService.Builder.getHttpServer().getDetailData(id, macId, 3)//android设备传3
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<InstituteDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "获取详情--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "获取详情--onError:" + e.toString());
                        listener.onDetailFailed(-1, "获取详情异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<InstituteDetailBean> bean) {
                        MLog.d(TAG, "获取详情-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onDetailSuccess(bean.getResult());
                        } else {
                            MLog.e("返回异常", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDetailFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 获取评论
     *
     * @param id
     * @param all
     * @param size
     * @param listener
     */
    public void mLoadCommentData(int id, int all, int size, String macId, final OnInstituteDetailListener listener) {

        MyHttpService.Builder.getHttpServer().getCommentData(id, all, size, macId, 3)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DetailCommentBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "获取评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "获取评论异常--onError:" + e.toString());
                        listener.onCommentFailed(-1, "获取评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailCommentBean> bean) {
                        MLog.d(TAG, "获取评论-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            MLog.d("评论数据：", bean.getResult().toString());
                            listener.onCommentSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onCommentFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 收藏
     */
    public void mCollect(int id, String token, final OnInstituteDetailListener listener) {

        MyHttpService.Builder.getHttpServer().collectPost(id, token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<String>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "收藏--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "收藏异常--onError:" + e.toString());
                        listener.onCollectFailed(-1, "收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<String> bean) {
                        MLog.d(TAG, "收藏-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onCollectSuccess("收藏成功！");
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onCollectFailed(bean.getCode(), bean.getMessage(), new Exception("收藏失败！"));
                        }
                    }

                });
    }

    /**
     * 取消收藏
     */
    public void mUnCollect(int id, String token, final OnInstituteDetailListener listener) {

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
                        listener.onUnCollectFailed(-1, "取消收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<String> bean) {
                        MLog.d(TAG, "取消收藏-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onUnCollectSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onUnCollectFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
