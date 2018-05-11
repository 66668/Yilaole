package com.yilaole.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.yilaole.utils.MLog;

/**
 * 百度地图servcie
 * Created by sjy on 2017/11/8.
 */

public class BaiduSDKService extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        String s = intent.getAction();
        MLog.d("百度地图", "action: " + s);
        if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
            MLog.e("key 验证出错! 错误码 :" + intent.getIntExtra(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0) + " ; 请在 AndroidManifest.xml 文件中检查 key 设置");
        } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
            MLog.d("key 验证成功! 功能可以正常使用");
        } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
            MLog.e("网络出错");
        } else {
            MLog.e("其他错误");
        }

    }
}
