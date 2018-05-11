package com.yilaole.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mobstat.StatService;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yilaole.http.HttpUtils;
import com.yilaole.http.WeichatHttpUtils;
import com.yilaole.http.cache.ACache;
import com.yilaole.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjy on 2017/4/18.
 */

public class MyApplication extends Application {
    private static MyApplication MyApplication;
    private boolean isLogin = false;

    public static String APP_ID = Constants.WEIXIN_ID;//微信的appID
    public static IWXAPI api;//微信api

    private List<Activity> listActOfALL = new ArrayList<Activity>();//退出app使用
    private List<Activity> listActOfSome = new ArrayList<Activity>();//关闭多个使用

    public static MyApplication getInstance() {
        return MyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;

        //设置打印,正式打包，设为 false
        MLog.init(true, "SJY");//true

        //初始化网络
        HttpUtils.getInstance().init(this, MLog.DEBUG);
        //微信初始化网络
        WeichatHttpUtils.getInstance().init(this, MLog.DEBUG);

        // 极光推送 SDK初始化
        //        JPushInterface.setDebugMode(true);//设置打印日志，测试用
        //        JPushInterface.init(this);

        /**
         * 百度统计
         *
         * 开启自动埋点统计，为保证所有页面都能准确统计，建议在Application中调用。第三个参数：autoTrackWebview：如果设置为true，则自动track所有webview；如果设置为false，则不自动track webview，
         * 如需对webview进行统计，需要对特定webview调用trackWebView() 即可。
         // 重要：如果有对webview设置过webchromeclient，则需要调用trackWebView() 接口将WebChromeClient对象传入， 否则开发者自定义的回调无法收到。
         */
        StatService.autoTrace(this, true, true);

        /**
         * 百度地图
         *
         */
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);


        /**
         * 微信登陆注册
         *
         * 初始化该操作放到baseActivity或者Application的onCrreate中
         */
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
    }

    //是否登录
    public boolean isLogin(Context context) {
        //        return isLogin;

        ACache aCache = ACache.get(context);//避免内存泄漏

        //token过期，返回false
        if (!aCache.getAsString(Constants.TOKEN).isEmpty()) {
            isLogin = true;
            return isLogin;
        } else {
            isLogin = false;
            return isLogin;
        }
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * application管理所有activity,暂不用广播
     */

    public void addActOfAll(Activity activity) {
        listActOfALL.add(activity);
        Log.d("SJY", "Current Acitvity Size :" + getCurrentActivitySize());
    }

    public void removeOneActOfAll(Activity activity) {
        listActOfALL.remove(activity);
        activity.finish();
        Log.d("SJY", "Current Acitvity Size :" + getCurrentActivitySize());
    }

    /**
     * 退出程序
     */
    public void exit() {
        for (Activity activity : listActOfALL) {
            activity.finish();
        }
    }

    /**
     * act的数量
     *
     * @return
     */
    public int getCurrentActivitySize() {
        return listActOfALL.size();
    }

    /**
     * 管理多个界面使用,不同于 管理所有界面
     */

    public void addOneActOfSome(Activity activity) {
        listActOfSome.add(activity);
    }

    public void removeOneActOfSome(Activity activity) {
        listActOfSome.remove(activity);
        activity.finish();
    }

    public void closeAllActOFSome() {
        for (Activity activity : listActOfSome) {
            activity.finish();
        }
        //清空数据
        listActOfSome.clear();
    }
}


