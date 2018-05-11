package com.yilaole.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 用于判断是不是联网状态
 *
 * @author Dzy
 */
public class CheckNetworkUtils {
    /***
     * 检查网络
     *
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对像
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                return !(info == null || !info.isAvailable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 判断网络是否连通
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            if(context!=null){
                @SuppressWarnings("static-access")
                ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null && info.isConnected();
            }else{
                /**如果context为空，就返回false，表示网络未连接*/
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && (info.getType() == ConnectivityManager.TYPE_WIFI);
        } else {
            /**如果context为null就表示为未连接*/
            return false;
        }

    }



}
