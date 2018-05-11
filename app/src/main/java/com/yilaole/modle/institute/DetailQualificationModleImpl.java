package com.yilaole.modle.institute;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.institution.detail.QualificationBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 资质机构
 */

public class DetailQualificationModleImpl {

    public void mLoadData(int id, String macid, final OnCommonListener listener) {

        MyHttpService.Builder.getHttpServer().getQualificaionData(id, macid, 3)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<QualificationBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "资质机构异常--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "资质机构异常--onError:" + e.toString());
                        listener.onDataFailed(-1, "资质机构异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<QualificationBean> bean) {
                        MLog.d(TAG, "资质机构异常-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDataSuccess(bean.getResult());
                        } else if (bean.getCode() == 404) {
                            //code = 0处理
                            listener.onDataFailed(bean.getCode(), bean.getMessage(), new Exception("获取资质机构失败！"));
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
