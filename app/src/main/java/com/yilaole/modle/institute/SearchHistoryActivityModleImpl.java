package com.yilaole.modle.institute;

import com.yilaole.bean.institution.filter.InstitutionListAreaBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 热门机构
 */

public class SearchHistoryActivityModleImpl {

    public void mLoadHotData(final OnCommonListener listener) {

        MyHttpService.Builder.getHttpServer().getHotData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InstitutionListAreaBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "SearchHistoryActivityModleImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "SearchHistoryActivityModleImpl--onError:" + e.toString());
                        listener.onDataFailed(-1,"获取热门机构异常", (Exception) e);
                    }

                    @Override
                    public void onNext(InstitutionListAreaBean bean) {
                        MLog.d(TAG, "SearchHistoryActivityModleImpl-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            MLog.d("获取数据长度：" + bean.getResult().size());
                            listener.onDataSuccess(bean.getResult());
                        } else if (bean.getCode() == 500) {
                            listener.onDataFailed(bean.getCode(),bean.getMessage(), new Exception("获取失败！"));
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
