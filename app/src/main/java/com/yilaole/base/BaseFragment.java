package com.yilaole.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yilaole.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

public abstract class BaseFragment extends Fragment {

    public LoadingDialog loadingDialog;
    public static final int MESSAGE_TOAST = 1001;
    protected boolean isVisible;
    public final Handler mHandler = new WeakRefHandler(this);

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        //弹窗显示登录状态
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setCanceledOnTouchOutside(false);//弹窗之外触摸无效
        loadingDialog.setCancelable(true);//true:可以按返回键back取消
    }

    /**
     * =================================================
     * ================设置异步handler操作==================
     * =================================================
     * 实际开发中handler会有内存泄露，使用弱引用避免
     */

    public class WeakRefHandler extends Handler {

        private final WeakReference<BaseFragment> mFragmentReference;

        public WeakRefHandler(BaseFragment fragment) {
            mFragmentReference = new WeakReference<BaseFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            final BaseFragment fragment = mFragmentReference.get();
            if (fragment != null) {
                fragment.handleMessage(msg);
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

    protected void sendMessage(Message msg) {
        mHandler.sendMessage(msg);
    }


    protected void sendMessage(int what) {
        mHandler.sendEmptyMessage(what);
    }

    public void sendMessage(int what, Object obj) {
        mHandler.sendMessage(mHandler.obtainMessage(what, obj));
    }

    public void sendToastMessage(int resId) {
        Message msg = new Message();
        msg.what = MESSAGE_TOAST;
        msg.obj = getString(resId);
        mHandler.sendMessage(msg);
    }

    public void sendToastMessage(String result) {
        Message msg = new Message();
        msg.what = MESSAGE_TOAST;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }

    /**
     * =================================================
     * ================设置Fragment懒加载操作==================
     * =================================================
     * <p>
     * 该方法先于onCreateView方法执行，要注意初始化问题
     */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
            setEnabled();
        } else {
            isVisible = false;
            onInvisible();
        }

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void setEnabled() {

    }

    protected void onInvisible() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);
    }
}
