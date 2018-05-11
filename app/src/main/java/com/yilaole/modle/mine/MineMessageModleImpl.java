package com.yilaole.modle.mine;

import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.mine.MessageItemBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 我的-我的消息
 */

public class MineMessageModleImpl {

    public void mGetMessageData(String token, int totol, int size, final OnCommonListener listener) {

        MyHttpService.Builder.getHttpServer().getMineMessageData(token, totol, size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<MessageItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "我的消息--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "我的消息--onError:" + e.toString());
                        listener.onDataFailed( -1,"我的消息异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<MessageItemBean> bean) {
                        MLog.d(TAG, "我的消息-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDataSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed( bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
