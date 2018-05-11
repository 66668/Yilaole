package com.yilaole.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import java.util.HashMap;

/**
 * 权限处理
 */

public class PermissionsUtil {
    private static final String TAG = "permission";
    private static HashMap<String, PermissionListener> listenerMap = new HashMap<>();

    /**
     * 申请授权，当用户拒绝时，显示默认的dialog提示用户,也可以设置提示用户的文本内容
     *
     * @param activity
     * @param listener
     * @param permission
     */
    public static void requestPermission(Activity activity, PermissionListener listener, String... permission) {
        requestPermission(activity, listener, permission, true, null);
    }

    public static void requestPermission(@NonNull Activity activity, @NonNull PermissionListener listener
            , @NonNull String[] permission, boolean showTip, @NonNull TipInfo tip) {

        if (listener == null) {
            Log.e(TAG, "listener is null");
            return;
        }

        //低于API23权限提醒设置
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {// Build.VERSION_CODES.M == android 6.0 ==API-23
            //检查是否授权
            if (PermissionsUtil.hasPermission(activity, permission)) {
                listener.permissionDenied(permission);
            } else {
                listener.permissionDenied(permission);
            }
            Log.d(TAG, "API level : " + Build.VERSION.SDK_INT + "不需要申请动态权限!");
            return;
        }
        String key = String.valueOf(System.currentTimeMillis());
        listenerMap.put(key, listener);

        Intent intent = new Intent(activity, PermissionActivity.class);
        intent.putExtra("permission", permission);
        intent.putExtra("key", key);
        intent.putExtra("showTip", showTip);
        intent.putExtra("tip", tip);
        activity.startActivity(intent);
    }

    /**
     * 判断是否授权
     */
    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        for (String per : permissions) {
            int result = PermissionChecker.checkSelfPermission(context, per);
            Log.d(TAG, "hasPermission: result=" + result);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一组授权结果是否授权通过
     */
    public static boolean isGranted(@NonNull int... grantedResult) {
        for (int result : grantedResult) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 跳转到当前应用对应的设置界面
     */
    public static void gotoSetting(@NonNull Context context) {
        //Settings 这里可以再修改
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);//跳转到应用程序的详细信息的界面
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 权限设置允许后，map中移除
     */
    public static PermissionListener removeThisListener(String key) {
        return listenerMap.remove(key);
    }


}
