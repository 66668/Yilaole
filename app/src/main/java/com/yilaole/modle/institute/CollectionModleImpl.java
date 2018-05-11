package com.yilaole.modle.institute;

import com.yilaole.bean.CommonListBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnCollectionListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 收藏m层
 */

public class CollectionModleImpl {

    public void mCollectPost(int id, String token, final OnCollectionListener listener) {

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
                        MLog.e(TAG, "收藏--onError:" + e.toString());
                        listener.onCollectionFailed(-1, "收藏异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<String> bean) {
                        MLog.d(TAG, "收藏-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onCollectionSuccess("收藏成功！");
                        } else if (bean.getCode() == 404) {
                            listener.onCollectionFailed(bean.getCode(), "404", new Exception("收藏失败！"));
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onCollectionFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
