package com.yilaole.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yilaole.base.app.MyApplication;

/**
 * actvitiy基类
 * 忘记密码的基类
 * <p>
 * 用于关闭打开的多个界面
 */

public class BaseLogActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addOneActOfSome(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeOneActOfSome(this);
        //界面消除时
        loadingDialog.dismiss();
    }
}
