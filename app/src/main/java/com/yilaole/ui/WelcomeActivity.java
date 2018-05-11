package com.yilaole.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 欢迎页
 */

public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 1500);
    }

    /**
     * 设置透明状态栏
     * //flag的详细用法见Readme讲解
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();

            //设置自动隐藏+手动显示的透明状态栏
            //            decorView.setSystemUiVisibility(
            //                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            //                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            //                            | View.SYSTEM_UI_FLAG_IMMERSIVE);


            //设置固定 透明状态栏
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void toMainActivity() {
        startActivity(MainActivity.class);
        this.finish();

    }

}