package com.yilaole.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yilaole.base.app.MyApplication;
import com.yilaole.dialog.LoadingDialog;
import com.yilaole.utils.MLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import lib.yilaole.takephoto.app.TakePhotoActivity;
import lib.yilaole.takephoto.model.TImage;
import lib.yilaole.takephoto.model.TResult;

/**
 * actvitiy基类
 * 继承了 libs的拍照功能，用于拍照使用
 */

public class BaseActivity extends TakePhotoActivity {
    //自定义弹窗 加载
    public LoadingDialog loadingDialog;
    public final Handler handler = new WeakRefHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        //弹窗显示登录状态
        loadingDialog = new LoadingDialog(BaseActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);//弹窗之外触摸无效
        loadingDialog.setCancelable(true);//true:可以按返回键back取消


        //将act保存,退出使用
        MyApplication.getInstance().addActOfAll(this);
    }

    /**
     * 用于处理 fragment多层嵌套的无返回处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int index = 0; index < fragmentManager.getFragments().size(); index++) {
            Fragment fragment = fragmentManager.getFragments().get(index); //找到第一层Fragment
            if (fragment == null)
                MLog.e("Activity result no fragment exists for index: 0x" + Integer.toHexString(requestCode));
            else
                handleResult(fragment, requestCode, resultCode, data);
        }
    }

    /**
     * 递归调用，对所有的子Fragment生效
     *
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @SuppressLint("RestrictedApi")
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null)
            MLog.e("BaseActivity");
    }

    /**
     * act页面跳转简化操作
     *
     * @param newClass
     */
    public void startActivity(Class<?> newClass) {
        Intent intent = new Intent(this, newClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // (2)
    public void startActivity(Class<?> newClass, Bundle extras) {
        Intent intent = new Intent(this, newClass);
        intent.putExtras(extras);
        //退出多个Activity的程序
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * =================================================
     * ================设置异步handler操作==================
     * =================================================
     */
    public static final int MESSAGE_TOAST = 1001;

    public class WeakRefHandler extends Handler {

        private final WeakReference<BaseActivity> mFragmentReference;

        public WeakRefHandler(BaseActivity activity) {
            mFragmentReference = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final BaseActivity activity = mFragmentReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    /**
     * @param msg
     */
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_TOAST:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出程序的广播
        //		unRegisterExitReceiver();
        MyApplication.getInstance().removeOneActOfAll(this);
        //界面消除时
        loadingDialog.dismiss();
    }

    /**
     * ======================================================================================
     * ================================================继承自TakePhotoActivity要使用的方法======================================
     * ======================================================================================
     */
    @Override
    public void takeCancel() {
        super.takeCancel();
    }


    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
//        Intent intent=new Intent(this,ResultActivity.class);
//        intent.putExtra("images",images);
//        startActivity(intent);
    }

}
