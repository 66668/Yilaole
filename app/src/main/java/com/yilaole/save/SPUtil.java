package com.yilaole.save;

import android.content.Context;
import android.content.SharedPreferences;

import com.yilaole.base.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreferences数据保存
 *
 * @author JackSong
 */
public class SPUtil {

    private static final String CONFIG = "efulai";

    private static final String USER_PHONE = "userPhone";//手机号
    private static final String IS_LOGIN = "isLogin";//是否登录
    private static final String USER_NAME = "userName";//用户名
    private static final String USER_PS = "password";//密码
    private static final String USER_LOCATION = "location";//城市定位
    private static final String USER_IMG = "userimage";//图片
    private static final String TOKEN_TIME = "token_time";//tokentime
    private static final String USER_TOKEN = "user_token";//token
    private static final String NEWS_TEXTSIZE = "news_textsize";//资讯详情 正文字体大小

    /**
     * 获取SharedPreferences实例对象
     *
     * @param fileName
     */
    private static SharedPreferences getSharedPreference(String fileName) {
        return MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * ==============================================================================
     * ======================================用户相关功能========================================
     * ==============================================================================
     */

    //用户名
    public static void setIsLogin(boolean isLogin) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putBoolean(IS_LOGIN, isLogin).apply();
    }

    public static boolean isLogin() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getBoolean(IS_LOGIN, false);
    }

    //是否保存用户名/昵称
    public static void setUserName(String name) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_NAME, name).apply();
    }

    public static String getUserName() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(USER_NAME, "");
    }

    //用户图片
    public static void setUserImage(String img) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_IMG, img).apply();
    }

    public static String getUserImage() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(USER_IMG, "");

    }

    //token保存时间
    public static void setTokenTime(int time) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putInt(TOKEN_TIME, time).apply();
    }

    public static int getTokenTime() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getInt(TOKEN_TIME, 0);
    }

    //token
    public static void setToken(String time) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_TOKEN, time).apply();
    }

    public static String getToken() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(USER_TOKEN, "");
    }

    //手机号
    public static void setUserPhone(String psd) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_PHONE, psd).apply();
    }

    public static String getUserPhone() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(USER_PHONE, "");
    }

    //密码
    public static void setPassword(String psd) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_PS, psd).apply();
    }

    public static String getPassword() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(USER_PS, "");
    }

    //城市定位
    public static void setLocation(String psd) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_LOCATION, psd).apply();
    }

    public static String getLocation() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(USER_LOCATION, "全国");
    }

    //资讯详情 字体大小设置 永久
    public static void setNewsSize(int psd) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putInt(NEWS_TEXTSIZE, psd).apply();
    }

    public static int getNewsSize() {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getInt(NEWS_TEXTSIZE, 18);
    }

    //清除用户登录信息
    public static void clearUserMessge() {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(USER_NAME, "").apply();
        editor.putString(USER_PHONE, "").apply();
        editor.putString(USER_PS, "").apply();
        editor.putBoolean(IS_LOGIN, false).apply();
    }

    /**
     * ==============================================================================
     * ======================================微信相关========================================
     * ==============================================================================
     */

    /**
     * ==============================================================================
     * ======================================通用功能========================================
     * ==============================================================================
     */
    /**
     * 保存一个String类型的值！
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(key, value).apply();
    }

    /**
     * 获取String的value
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(key, defValue);
    }

    /**
     * 保存一个Boolean类型的值！
     */
    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putBoolean(key, value).apply();
    }

    /**
     * 获取boolean的value
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * 保存一个int类型的值！
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putInt(key, value).apply();
    }

    /**
     * 获取int的value
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getInt(key, defValue);
    }

    /**
     * 保存一个float类型的值！
     */
    public static void putFloat(String fileName, String key, float value) {
        SharedPreferences.Editor editor = getSharedPreference(fileName).edit();
        editor.putFloat(key, value).apply();
    }

    /**
     * 获取float的value
     */
    public static float getFloat(String key, Float defValue) {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getFloat(key, defValue);
    }

    /**
     * 保存一个long类型的值！
     */
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putLong(key, value).apply();
    }

    /**
     * 获取long的value
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getLong(key, defValue);
    }

    /**
     * 取出List<String>
     *
     * @param key List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(String key) {
        List<String> strList = new ArrayList<String>();
        int size = getInt(key + "size", 0);
        //Log.d("sp", "" + size);
        for (int i = 0; i < size; i++) {
            strList.add(getString(key + i, ""));
        }
        return strList;
    }

    /**
     * 存储List<String>
     *
     * @param key     List<String>对应的key
     * @param strList 对应需要存储的List<String>
     */
    public static void putStrListValue(String key, List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
        removeStrList(key);
        int size = strList.size();
        putInt(key + "size", size);
        for (int i = 0; i < size; i++) {
            putString(key + i, strList.get(i));
        }
    }

    /**
     * 清空List<String>所有数据
     *
     * @param key List<String>对应的key
     */
    public static void removeStrList(String key) {
        int size = getInt(key + "size", 0);
        if (0 == size) {
            return;
        }
        remove(key + "size");
        for (int i = 0; i < size; i++) {
            remove(key + i);
        }
    }

    /**
     * 清空对应key数据
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.remove(key).apply();
    }
}


