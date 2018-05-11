package com.yilaole.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sjy on 2017/11/14.
 */

public class TimeUtils {
    /**
     * 获取日期
     * int[]保存eg:[2017,11,11]
     */
    public static int[] getYearMonthDayInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{cal.get(Calendar.YEAR)
                , cal.get(Calendar.MONTH) + 1
                , cal.get(Calendar.DATE)};
    }

    /**
     * 获取日期
     * string[]保存eg:["2017","11","11"]
     */
    public static String[] getYearMonthDayStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new String[]{cal.get(Calendar.YEAR) + ""
                , cal.get(Calendar.MONTH) + 1 + ""
                , cal.get(Calendar.DATE) + ""};
    }

    /**
     * 比较两个时间的大小，参数格式必须是 HH:mm:ss
     *
     * @return
     */
    public static boolean isTimesBiger(String time1, String time2) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date dt1 = df.parse(time1);//将字符串转换为date类型
            Date dt2 = df.parse(time2);
            Log.d("SJY", "isTimesBiger: " + dt1.getTime() + "--" + dt2.getTime());
            //比较时间大小,如果dt1大于dt2
            return dt1.getTime() > dt2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SJY", "isTimesBiger: " + e.toString());
        }
        return true;
    }

    /**
     * 获取时间戳 单位（秒）
     *
     * @return
     */
    public static Integer getTimeStamp() {
        return ((Long) (System.currentTimeMillis() / 1000)).intValue();
    }

    /**
     * 获取时间戳 单位（毫秒）
     *
     * @return
     */
    public static long getMillisTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 把时间戳(毫秒)转成日期字符串
     *
     * @param timeStamp
     * @return yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String toDateTime(long timeStamp) {
        Timestamp ts = new Timestamp(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 把时间戳(毫秒)转成日期字符串
     *
     * @param timeStamp
     * @return yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String toDate(long timeStamp) {
        Timestamp ts = new Timestamp(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        Date date = new Date();
        String strDate = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA).format(date));
        return strDate;
    }

    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        Date date = new Date();
        String strDate = new String(new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA).format(date));
        return strDate;
    }

    /**
     * 是否是同一天
     *
     * @param t1 毫秒
     * @param t2 毫秒
     * @return
     */
    public static boolean isSameDay(long t1, long t2) {
        return getDate(t1, "yyyy-MM-dd").contains(getDate(t2, "yyyy-MM-dd"));
    }

    /**
     * 是否是同一小时
     *
     * @param t1 毫秒
     * @param t2 毫秒
     * @return
     */
    public static boolean isSameHour(long t1, long t2) {
        return getDate(t1, "yyyy-MM-dd HH").contains(getDate(t2, "yyyy-MM-dd HH"));
    }

    /**
     * 毫秒转指定日期格式
     *
     * @param datetime 毫秒
     * @param format
     * @return
     */
    public static String getDate(long datetime, String format) {
        Date date = new Date(datetime);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(date);

    }

    /**
     * 毫秒转指定日期格式yyyy-MM-dd HH:mm:ss
     *
     * @param datetime 毫秒
     * @return
     */
    public static String getDate(long datetime) {
        return getDate(datetime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期yyyy-MM-dd HH:mm:ss转毫秒
     *
     * @param data
     * @return
     */
    public static long getMillsOfDate(String data) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }


    /**
     * 计算两个日期的天数差
     *
     * @param fromDate yyyy-MM-dd
     * @param toDate   yyyy-MM-dd
     * @param custom
     * @return
     */
    public static int dateDiff(String fromDate, String toDate, int custom) {

        // return customDay;
        int days = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            java.util.Date from = df.parse(fromDate);
            java.util.Date to = df.parse(toDate);
            days = (int) Math.abs((to.getTime() - from.getTime())
                    / (24 * 60 * 60 * 1000)) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 小时差（只比较HH）
     *
     * @param fromDate yyyy-MM-dd HH:mm:ss
     * @param toDate   yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int HourDiff(String fromDate, String toDate) {

        int hour = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH", Locale.CHINA);
            java.util.Date from = df.parse(fromDate);
            java.util.Date to = df.parse(toDate);
            hour = (int) Math.abs((to.getTime() - from.getTime()) / (60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour;
    }

    /**
     * 分钟差（只比较mm）
     *
     * @param fromDate yyyy-MM-dd HH:mm:ss
     * @param toDate   yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int miniteDiff(String fromDate, String toDate) {

        int min = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            java.util.Date from = df.parse(fromDate);
            java.util.Date to = df.parse(toDate);
            min = (int) Math.abs((to.getTime() - from.getTime()) / 60 / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return min;
    }


    /**
     * 离手机当前时间一小时以内的，显示  xx分钟以前 ； 离当前时间1天以内，1小时以上的，显示 x小时前 ； 离当前时间大于1天的，显示 具体日期，比如 2017-08-21 这样
     *
     * @param oldTime 格式yyyy-MM-dd HH:mm:ss
     */
    public static String getExactelyTime(String oldTime) {

        long currentTimeMills = getMillisTimeStamp();
        long oldTimeMills = getMillsOfDate(oldTime);
        String currentTime = toDateTime(currentTimeMills);
        //是否是同一天
        if (isSameDay(currentTimeMills, oldTimeMills)) {
            //是否是同一个小时
            MLog.d("同一个小时", getDate(oldTimeMills, "yyyy-MM-dd HH"), getDate(currentTimeMills, "yyyy-MM-dd HH"));
            if (isSameHour(currentTimeMills, oldTimeMills)) {
                //获取分钟差
                return (miniteDiff(oldTime, currentTime) + "分钟前");

            } else {
                //获取小时差：x小时前
                return (HourDiff(oldTime, currentTime) + "小时前");

            }

        } else {
            //返回完整日期  yyyy-MM-dd
            return toDate(oldTimeMills);
        }


    }
}
