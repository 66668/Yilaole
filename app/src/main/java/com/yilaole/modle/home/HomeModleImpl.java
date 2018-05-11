package com.yilaole.modle.home;

import com.yilaole.base.app.Constants;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.home.BannerBean;
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.bean.home.SearchBean;
import com.yilaole.bean.home.TextLooperBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnNavHomeListener;
import com.yilaole.inter_face.imodle.INavHomeModle;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 资讯 m层实现
 */

public class HomeModleImpl implements INavHomeModle {
    public static final String TAG = "HOME";


    /**
     * 01 搜索轮播
     *
     * @param listener
     */

    @Override
    public void mLoadSearchData(String city, final OnNavHomeListener listener) {

        MyHttpService.Builder.getHttpServer().getSearchData(city)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<SearchBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "搜索轮播--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "HomeModleImpl--搜索轮播异常onError:" + e.toString());
                        listener.onLooperSearchFailed(-1, "获取主页搜索轮播数据异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<SearchBean> bean) {
                        MLog.d(TAG, "搜索轮播--onNext");
                        MLog.d(TAG, "搜索轮播--搜索轮播" + bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onLooperSearchSuccess(bean.getResult());
                            MLog.d("搜索轮播：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onLooperSearchFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 02 轮播图
     *
     * @param listener
     */
    @Override
    public void mLoadLooperImgData(final OnNavHomeListener listener, String city) {

        MyHttpService.Builder.getHttpServer().getBanner(city, Constants.ANDROID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<BannerBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "HomeModleImpl--onCompleted 获取主页轮播图数据成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "HomeModleImpl--获取主页轮播图数据异常onError:" + e.toString());
                        listener.onLooperImgFailed(-1, "获取主页轮播图数据异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<BannerBean> bean) {
                        MLog.d(TAG, "HomeModleImpl--轮播图--" + bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onLooperImgSuccess(bean.getResult());
                            MLog.d("轮播图数据长度：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onLooperImgFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 03 文字轮播
     *
     * @param listener
     */
    @Override
    public void mLoadLooperTextData(String city, final OnNavHomeListener listener) {

        MyHttpService.Builder.getHttpServer().getTextData(city)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<TextLooperBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "HomeModleImpl--onCompleted文字轮播--成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "HomeModleImpl--文字轮播数据--异常onError:" + e.toString());
                        listener.onLooperTextFailed(-1, "文字轮播数据异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<TextLooperBean> bean) {
                        MLog.d(TAG, "文字轮播" + bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onLooperTextSuccess(bean.getResult());
                            MLog.d("文字轮播数据长度：" + bean.getResult().size());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onLooperTextFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }


    /**
     * 04首页资讯
     *
     * @param listener
     */
    @Override
    public void mLoadHorizontalNewstData(final OnNavHomeListener listener, String city) {

        MyHttpService.Builder.getHttpServer().getHomeNewsData(city)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<HomeNewsBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "HomeModleImpl--onCompleted-首页资讯--成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "HomeModleImpl--首页资讯异常onError:" + e.toString());
                        listener.onNewsFailed(-1,"首页资讯异常--", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<HomeNewsBean> bean) {

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            MLog.d(TAG, "HomeModleImpl-首页资讯200");
                            listener.onNewsSuccess(bean.getResult());
                        }  else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onNewsFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 05首页tab
     *
     * @param listener
     */
    @Override
    public void mLoadHomeTabData(String city,final OnNavHomeListener listener) {

        MyHttpService.Builder.getHttpServer().getHomeTabData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<HomeTabBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "HomeModleImpl--onCompleted-首页tab--成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "HomeModleImpl--首页tab异常onError:" + e.toString());
                        listener.onHomeTabFailed(-1,"首页tab异常--", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<HomeTabBean> bean) {

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            MLog.d(TAG, "HomeModleImpl-首页tab200");
                            listener.onHomeTabSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onHomeTabFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
