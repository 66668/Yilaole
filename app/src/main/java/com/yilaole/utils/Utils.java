package com.yilaole.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.yilaole.base.app.MyApplication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 工具类
 */
public class Utils {

    /**
     * 密码验证
     * <p>
     * ^[a-zA-Z][a-zA-Z0-9_-]{5,15}$
     * <p>
     * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,15}$
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        return password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,15}$");
    }


    //*****************************************************网络验证************************************************

    /**
     * 获取网络信息
     *
     * @return
     */
    public static String getNetworkInfo() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) MyApplication
                    .getInstance().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            String typeName = info[i].getTypeName();
                            if (typeName.equals("WIFI")) {
                                WifiManager wifiManager = (WifiManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                typeName += " SSID:" + wifiInfo.getSSID() + " MAC:" + wifiInfo.getMacAddress();
                                return typeName;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取当前网络状态
     *
     * @return NetworkInfo
     */
    public static NetworkInfo getCurrentNetStatus(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    /**
     * 获取网络连接状态
     *
     * @param ctx
     * @return true:有网 false：没网
     */
    public static boolean isNetworkAvailable(Context ctx) {
        NetworkInfo nki = getCurrentNetStatus(ctx);
        if (nki != null) {
            return nki.isAvailable();
        } else
            return false;
    }

    //*****************************************************唯一标识的几种获取方式************************************************

    /**
     * WLAN MAC Address(常用方式)
     * 需要加入android.permission.ACCESS_WIFI_STATE 权限
     * eg:34:14:5F:F7:31:8F
     *
     * @param context
     * @return
     */
    public synchronized static String getMacId(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String WLANMAC = wm.getConnectionInfo().getMacAddress();

        return WLANMAC;
    }

    /**
     * wifi方式获取mac
     * 需要加入android.permission.ACCESS_WIFI_STATE 权限
     * eg:34:14:5f:f7:31:8f
     *
     * @return
     */
    public static String getMacIdByWifi() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 使用蓝牙地址作为标识
     * 要加入android.permission.BLUETOOTH 权限
     *
     * @param context
     * @return
     */
    public synchronized static String getBlueToothId(Context context) {
        BluetoothAdapter mBlueth = BluetoothAdapter.getDefaultAdapter();
        String mBluethId = mBlueth.getAddress();
        return mBluethId;
    }


    /**
     * 获取当前手机ip地址
     *
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                Log.d("SJY", "2G/3G/4G网络ip=" + inetAddress.getHostAddress());
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                Log.d("SJY", "无线网络ip=" + ipAddress);
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 使用IMEI当唯一标识
     * 但是仅仅对Android手机有效，并且添加权限:android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return
     */

    //    public synchronized static String getIMEIId(Context context) {
    //        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    //        String ID = telephonyMgr.getDeviceId();
    //        return ID;
    //    }

    /**
     * 获取手机卡供应商名字
     * <p>
     * 但是仅仅对Android手机有效，并且添加权限:android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return
     */

    //    public String getProvidersName(Context context) {
    //        String ProvidersName = "";
    //        TelephonyManager telephonyManager = (TelephonyManager) context
    //                .getSystemService(Context.TELEPHONY_SERVICE);
    //        // 返回唯一的用户ID;就是这张卡的编号
    //        String IMSI = telephonyManager.getSubscriberId();
    //        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
    //        System.out.println(IMSI);
    //        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
    //            ProvidersName = "中国移动";
    //        } else if (IMSI.startsWith("46001")) {
    //            ProvidersName = "中国联通";
    //        } else if (IMSI.startsWith("46003")) {
    //            ProvidersName = "中国电信";
    //        }
    //        return ProvidersName;
    //
    //    }


    //***********************************************其他信息获取/手机信息相关******************************************************

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取手机厂商
     *
     * @return
     */
    public static String getPhonePRODUCT() {
        try {
            return android.os.Build.PRODUCT;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * /**
     * 获取手机型号
     * eg：xiaomi6
     *
     * @return
     */
    public static String getPhoneModel() {
        try {
            return android.os.Build.MODEL;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * MD5
     *
     * @param string
     * @return
     */
    public static String getMd5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取API版本
     *
     * @return
     */
    public static int getAPILevel() {
        return Integer.parseInt(Build.VERSION.SDK);
    }

    /**
     * @return
     */
    public static String getAndroidVersion() {

        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取可用存储空间大小 若存在SD卡则返回SD卡剩余空间大小 否则返回手机内存剩余空间大小
     *
     * @return
     */
    public static long getAvailableStorageSpace() {
        long externalSpace = getExternalStorageSpace();
        if (externalSpace == -1L) {
            return getInternalStorageSpace();
        }

        return externalSpace;
    }

    /**
     * 获取机器内部可用空间 getAvailableStorageSpace()
     *
     * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
     */
    //CreateUserActivity--UpdateAvatar--MyApplication--Utils该方法
    public static long getInternalStorageSpace() {
        long availableInternalSpace = -1L;//-1L:没有SD卡

        StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = sf.getBlockSize();// 块大小,单位byte
        long availCount = sf.getAvailableBlocks();// 可用块数量
        availableInternalSpace = availCount * blockSize / 1024 / 1024;// 可用SD卡空间，单位MB

        return availableInternalSpace;
    }

    /**
     * 获取SD卡可用空间 getAvailableStorageSpace()
     *
     * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
     */
    public static long getExternalStorageSpace() {
        long availableSDCardSpace = -1L;
        // 存在SD卡
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
                    .getPath());
            long blockSize = sf.getBlockSize();// 块大小,单位byte
            long availCount = sf.getAvailableBlocks();// 可用块数量
            availableSDCardSpace = availCount * blockSize / 1024 / 1024;// 可用SD卡空间，单位MB
        }

        return availableSDCardSpace;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {

        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {

        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
