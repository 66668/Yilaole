package com.yilaole.modle.institute;

import com.yilaole.bean.CommonListBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 创建 上门评估
 */

public class DetailDoorAssessModleImpl {

    public void mPostData(int id,
                          String name,
                          String token,
                          String contact_name,
                          String contact_way,
                          String address,
                          int elder_id,
                          String visit_time,
                          String remark,
                          final OnCommonListener listener) {

        MyHttpService.Builder.getHttpServer().postDoorAssessData(id
                , name
                , token
                , contact_name
                , contact_way
                , address
                , elder_id
                , visit_time
                , remark)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<String>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "上门评估--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "上门评估异常--onError:" + e.toString());
                        listener.onDataFailed(-1,"上门评估异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<String> bean) {
                        MLog.d(TAG, "上门评估-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onDataSuccess(bean.getResult());
                        } else if (bean.getCode() == 401) {
                            //code = 0处理
                            listener.onDataFailed(bean.getCode(),"401", new Exception("获取上门评估失败！"));
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onDataFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }


}
