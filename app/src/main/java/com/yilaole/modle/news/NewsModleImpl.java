package com.yilaole.modle.news;

import com.yilaole.base.app.Constants;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.news.NewsBannerBean;
import com.yilaole.bean.news.NewsBean;
import com.yilaole.bean.news.NewsTabBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.ilistener.OnNavNewsListener;
import com.yilaole.inter_face.imodle.INavNewsModle;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 资讯 m层实现
 */

public class NewsModleImpl implements INavNewsModle {
    public static final String TAG = "SJY";

    /**
     * 获取tab数据
     *
     * @param listener
     */
    @Override
    public void mLoadTabData(final OnNavNewsListener listener) {

        MyHttpService.Builder.getHttpServer().getNewsTabData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<NewsTabBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "NewsModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--获取tab失败异常onError:" + e.toString());
                        listener.onTabDataFailed(-1, "获取tab失败异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<NewsTabBean> bean) {
                        MLog.d(TAG, "NewsModleImpl-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            MLog.d("获取数据长度：" + bean.getResult().size());
                            listener.onTabDataSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onTabDataFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 获取轮播图数据
     *
     * @param listener
     */
    @Override
    public void mLoadLooperImgData(final OnNavNewsListener listener, String city) {

        MyHttpService.Builder.getHttpServer().getNewsBanner(city, Constants.ANDROID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<NewsBannerBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "NewsModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--获取轮播异常onError:" + e.toString());
                        listener.onLooperImgFailed(-1, "获取轮播异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<NewsBannerBean> bean) {
                        MLog.d(TAG, "NewsModleImpl-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onLooperImgSuccess(bean.getResult());
                            MLog.d("轮播图数据长度：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onLooperImgFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * =======================================================================================================================
     * ============================================================上拉下拉设计===========================================================
     * =======================================================================================================================
     */
    /**
     * 获取列表数据
     *
     * @param listener
     */
    @Override
    public void mLoadListData(final OnMoreAndRefreshListener listener, int pageTotle, int pageSize, int type) {

        MyHttpService.Builder.getHttpServer().postNewsListData(pageTotle, pageSize, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<NewsBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "NewsModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--获取列表数据异常onError:" + e.toString());
                        listener.onListFailed(-1, "获取list异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<NewsBean> bean) {
                        MLog.d(TAG, "NewsModleImpl-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onListSuccess(bean.getResult());
                            MLog.d("list数据长度：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onListFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 加载更多
     *
     * @param listener
     * @param pageTotle
     * @param pageSize
     * @param type
     */
    @Override
    public void mMoreListData(final OnMoreAndRefreshListener listener, int pageTotle, int pageSize, int type) {
        MyHttpService.Builder.getHttpServer().postNewsListData(pageTotle, pageSize, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<NewsBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "NewsModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--获取列表数据异常onError:" + e.toString());
                        listener.onMoreFailed(-1, "获取list异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<NewsBean> bean) {
                        MLog.d(TAG, "NewsModleImpl-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onMoreSuccess(bean.getResult());
                            MLog.d("list数据长度：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onMoreFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 刷新
     *
     * @param listener
     * @param pageTotle
     * @param pageSize
     * @param type
     */

    @Override
    public void mRefreshListData(final OnMoreAndRefreshListener listener, int pageTotle, int pageSize, int type) {
        MyHttpService.Builder.getHttpServer().postNewsListData(pageTotle, pageSize, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<NewsBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "NewsModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "NewsModleImpl--获取列表数据异常onError:" + e.toString());
                        listener.onRefreshFailed(-1, "获取list异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<NewsBean> bean) {
                        MLog.d(TAG, "NewsModleImpl-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onRefreshSuccess(bean.getResult());
                            MLog.d("list数据长度：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onRefreshFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

}
