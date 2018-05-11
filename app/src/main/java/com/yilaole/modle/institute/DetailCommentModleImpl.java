package com.yilaole.modle.institute;

import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.institution.detail.DetailCommentBean;
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
 * 机构评论 获取列表/发表评论
 */

public class DetailCommentModleImpl {


    /**
     * 发表评论 无图上传
     */
    public void mPostDataWithoutPic(int id, int order_id, String token, int agency_score, int servoce_score, String content, int type, final OnCommonListener listener) {
        MLog.d("发表评论上传参数", "id=" + id + "\n"
                + "order_id=" + order_id + "\n"
                + "token=" + token + "\n"
                + "type=" + type + "\n"
                + "agency_score=" + agency_score + "\n"
                + "servoce_score=" + servoce_score + "\n"
                + "content=" + content + "\n"
                + "file0=" + null + "\n"
        );

        MyHttpService.Builder.getHttpServer().postCommentWithoutPic(id, order_id, token, agency_score, servoce_score, content, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DetailCommentBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "发表评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "发表评论异常--onError:" + e.toString());
                        listener.onDataFailed(-1, "发表评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailCommentBean> bean) {
                        MLog.d(TAG, "发表评论-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDataSuccess(bean.getResult());
                        } else if (bean.getCode() == 400) {
                            MLog.d(bean.getCode(), bean.getMessage());
                            listener.onDataFailed(bean.getCode(), "400", new Exception("发表评论失败！"));
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 发表评论 有图上传
     */
    public void mPostDataWithPic(int id, int order_id, String token, int agency_score, int servoce_score, String content, int type, Object file0, Object file1, Object file2, final OnCommonListener listener) {
        MLog.d("发表评论上传参数", "id=" + id + "\n"
                + "order_id=" + order_id + "\n"
                + "token=" + token + "\n"
                + "type=" + type + "\n"
                + "agency_score=" + agency_score + "\n"
                + "servoce_score=" + servoce_score + "\n"
                + "content=" + content + "\n"
                + "file0=" + "有图" + "\n"
        );

        MyHttpService.Builder.getHttpServer().postCommentWithPic(id, order_id, token, agency_score, servoce_score, content, type, null)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<DetailCommentBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "发表评论--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "发表评论异常--onError:" + e.toString());
                        listener.onDataFailed(-1, "发表评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailCommentBean> bean) {
                        MLog.d(TAG, "发表评论-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDataSuccess(bean.getResult());
                        } else if (bean.getCode() == 400) {
                            MLog.d(bean.getCode(), bean.getMessage());
                            listener.onDataFailed(bean.getCode(), "400", new Exception("发表评论失败！"));
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
    /**
     * ===================================================================================================================
     * ========================================================上拉下拉===========================================================
     * ===================================================================================================================
     */
    /**
     * 获取评论列表
     */

    public void mLoadCommentData(int id, int all, int size, String macId, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getCommentData(id, all, size, macId, 3)//安卓设备写3
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
                        listener.onListFailed(-1, "获取评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailCommentBean> bean) {
                        MLog.d(TAG, "获取评论-onNext");
                        MLog.d(TAG, bean.toString());

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

    /**
     * 加载
     */

    public void mMoreData(int id, int all, int size, String macid, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getCommentData(id, all, size, macid,3)
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
                        listener.onMoreFailed(-1, "获取评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailCommentBean> bean) {
                        MLog.d(TAG, "获取评论-onNext");
                        MLog.d(TAG, bean.toString());

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

    /**
     * 刷新
     */

    public void mRefreshData(int id, int all, int size, String macid, final OnMoreAndRefreshListener listener) {

        MyHttpService.Builder.getHttpServer().getCommentData(id, all, size, macid,3)
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
                        listener.onRefreshFailed(-1, "获取评论异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<DetailCommentBean> bean) {
                        MLog.d(TAG, "获取评论-onNext");
                        MLog.d(TAG, bean.toString());

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
