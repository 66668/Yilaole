package com.yilaole.modle.home;

import com.yilaole.base.app.Constants;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.home.HomeInstituteItemBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnHomeInstitutionAreaListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 首页-嵌套机构fragment m层实现
 */

public class HomeInstitutionModleImpl {

    /**
     * 获取嵌套机构数据
     *
     * @param listener
     * @param type
     * @param city_name
     * @param token
     */
    public void mLoadData(final OnHomeInstitutionAreaListener listener, String type, String city_name, String token) {
        MLog.d("获取机构参数：", type, city_name, token, Constants.ANDROID);
        MyHttpService.Builder.getHttpServer().postTabData(type, city_name, token, Constants.ANDROID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<HomeInstituteItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "嵌套机构数据--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e("获取tab数据失败", "嵌套机构数据--onError:" + e.toString());
                        listener.onTabDataFailed(-1,"获取失败异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<HomeInstituteItemBean> bean) {
                        MLog.d(TAG, "嵌套机构数据-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            MLog.d("嵌套机构数据-------------------------："+bean.getCode());
                            listener.onTabDataSuccess(bean.getResult());
                        }  else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onTabDataFailed(bean.getCode(),bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

}
