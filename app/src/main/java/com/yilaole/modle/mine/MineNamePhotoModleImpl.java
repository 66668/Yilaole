package com.yilaole.modle.mine;

import com.yilaole.bean.CommonBean;
import com.yilaole.bean.mine.PhotoBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnChangeNameListener;
import com.yilaole.inter_face.ilistener.OnChangePhotoListener;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 我的-修改昵称 头像
 */

public class MineNamePhotoModleImpl {

    public void mChangeName(String token, String name, final OnChangeNameListener listener) {

        MyHttpService.Builder.getHttpServer().changeNamePost(token, name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "修改昵称--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "修改昵称--onError:" + e.toString());
                        listener.onChangeNameFailed(-1, "修改昵称异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        MLog.d(TAG, "修改昵称-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onChangeNameSuccess("修改成功");
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onChangeNameFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 修改 头像
     *
     * @param token    该参数需要加载到RequestBody中
     * @param file
     * @param listener
     */
    public void mChangePhoto(String token, File file, final OnChangePhotoListener listener) {
        //需要对file进行封装

        //token
        // 需要加入到MultipartBody中，而不是作为参数传递
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", token);
        //file
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("avatar", file.getName(), photoRequestBody);

        List<MultipartBody.Part> parts = builder.build().parts();

        MyHttpService.Builder.getHttpServer().changePhotoPost(parts)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<PhotoBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "修改头像--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "修改头像异常--onError:" + e.toString());
                        listener.onChangePhoteFailed(-1, "修改头像异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<PhotoBean> bean) {
                        MLog.d(TAG, "修改头像-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onChangePhoteSuccess(bean.getResult());
                        } else {
                            MLog.e("返回数据错误", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onChangePhoteFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
}
