package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.inter_face.ilistener.OnChangeNameListener;
import com.yilaole.inter_face.ilistener.OnChangePhotoListener;
import com.yilaole.inter_face.ipresenter.IChangeNamePresenter;
import com.yilaole.modle.mine.MineNamePhotoModleImpl;

import java.io.File;

/**
 * 修改昵称 头像 p层实现
 */

public class MineNamePhotoPresenterImpl implements IChangeNamePresenter, OnChangeNameListener, OnChangePhotoListener {
    Context context;
    OnChangeNameListener view;
    OnChangePhotoListener photoView;
    MineNamePhotoModleImpl modle;

    public MineNamePhotoPresenterImpl(Context context, OnChangeNameListener view) {
        this.context = context;
        this.view = view;
        modle = new MineNamePhotoModleImpl();
    }

    public MineNamePhotoPresenterImpl(Context context, OnChangePhotoListener view) {
        this.context = context;
        this.photoView = view;
        modle = new MineNamePhotoModleImpl();
    }


    /**
     * 修改昵称
     *
     * @param token
     * @param name
     */

    @Override
    public void pChangeNamePost(String token, String name) {
        modle.mChangeName(token, name, this);
    }

    /**
     * 修改头像
     */

    @Override
    public void pChangePhotoPost(String token, File file) {
        modle.mChangePhoto(token, file, this);
    }


    @Override
    public void onChangeNameSuccess(Object obj) {
        view.onChangeNameSuccess(obj);
    }

    @Override
    public void onChangeNameFailed(int code, String msg, Exception e) {
        view.onChangeNameFailed(code, msg, e);

    }

    @Override
    public void onChangePhoteSuccess(Object obj) {
        photoView.onChangePhoteSuccess(obj);
    }

    @Override
    public void onChangePhoteFailed(int code, String msg, Exception e) {
        photoView.onChangePhoteFailed(code, msg, e);
    }
}
