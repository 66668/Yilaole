package com.yilaole.ui;

import android.Manifest;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.baidu.mapapi.SDKInitializer;
import com.yilaole.R;
import com.yilaole.adapter.MainViewPagerAdapter2;
import com.yilaole.base.BaseActivity;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.permissions.PermissionListener;
import com.yilaole.permissions.PermissionsUtil;
import com.yilaole.service.BaiduSDKService;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    //viewpager
    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;

    //bottom
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int currentFragment = 0;//默认首页是当前
    private MainViewPagerAdapter2 adapter;
    private BaiduSDKService mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        viewPager = findViewById(R.id.view_pager);
        ButterKnife.bind(this);

        initRxBus();
        initMyView();

        //读写相关 需要权限
        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//ACCESS_FINE_LOCATION ACCESS_COARSE_LOCATION这两个是一组，用一个判断就够了
            //授权通过
            initFragmentShow();
        } else {
            //第一次使用该权限调用
            PermissionsUtil.requestPermission(this
                    , new PermissionListener() {
                        //授权通过
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            initFragmentShow();
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            ToastUtil.toastLong(MainActivity.this, "请确保读写权限，否则无法正常使用");
                        }
                    }
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        // 注册 百度SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new BaiduSDKService();
        registerReceiver(mReceiver, iFilter);

    }

    private void initFragmentShow() {
        //初始化
        adapter = new MainViewPagerAdapter2(getSupportFragmentManager());
        //        if (savedInstanceState != null) {
        //            adapter.restoreFragments(savedInstanceState);
        //        } else {
        //            adapter.initFragments();
        //        }
        viewPager.setAdapter(adapter);
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
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void initMyView() {

        //添加底部导航栏
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.bottom_navigation_item_home, R.mipmap.icon_home_page, R.color.colorBottomNavigationActiveColored);
        //        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.bottom_navigation_item_institute, R.mipmap.icon_home_filter, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.bottom_navigation_item_news, R.mipmap.icon_information_page, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.bottom_navigation_item_mine, R.mipmap.icon_mine, R.color.colorBottomNavigationActiveColored);

        bottomNavigationItems.add(item1);
        //        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));//选中背景色
        bottomNavigation.setAccentColor(ContextCompat.getColor(MainActivity.this, R.color.commonColor));//选中颜色
        bottomNavigation.setInactiveColor(ContextCompat.getColor(MainActivity.this, R.color.commonUnselectColor));//未选中颜色

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, false);

                currentFragment = position;
                MLog.d("当前fragment:" + currentFragment);
                return true;
            }
        });

        viewPager.setOffscreenPageLimit(3);

    }


    /**
     * =============================================================================
     * =====================================生命周期========================================
     * =============================================================================
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    //    @Override
    //    protected void onSaveInstanceState(Bundle outState) {
    //        super.onSaveInstanceState(outState);
    //        adapter.saveFragments(outState);
    //    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听 百度地图SDK 广播
        unregisterReceiver(mReceiver);

        if (adapter != null) {
            adapter = null;
        }
        if (bottomNavigationItems != null) {
            bottomNavigationItems = null;
        }
    }

    //    @Override
    //    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    //        super.onSaveInstanceState(outState, outPersistentState);
    //    }

    /**
     * 使用RxBus 实现fragment之间的跳转
     * home页跳转到new页
     */
    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_NEWS, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        bottomNavigation.setCurrentItem(1);
                        viewPager.setCurrentItem(1, false);

                        currentFragment = 1;
                        MLog.d("当前fragment:" + currentFragment);
                    }
                });
    }
}