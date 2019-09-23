package com.hy.frame.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * title 时间工具
 * author heyan
 * time 19-7-8 下午5:45
 * desc 无
 */
public final class DateUtil {
    /**
     * 获取当前时间Date "yyyy-MM-dd HH:mm:ss"
     *
     * @return 现在时间(Now)
     */
    public static String getNowTime() {
        return getNowTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间Date "yyyy-MM-dd HH:mm:ss"
     *
     * @param type 指定格式
     * @return 现在时间(Now)
     */
    public static String getNowTime(String type) {
        return getDateTime(new Date(System.currentTimeMillis()), type);
    }

    /**
     * 获取当前时间Date
     *
     * @param date 时间
     * @param type 时间格式 "yyyy-MM-dd HH:mm:ss"
     * @return 字符串时间
     */
    public static String getDateTime(Date date, String type) {
        if (date == null) return "";
        String pattern = type == null ? "yyyy-MM-dd HH:mm:ss" : type;
        DateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
        return df.format(date);
    }

    /**
     * 获取当前时间Date
     *
     * @param time 时间戳 可以是十位数
     * @param type 时间格式 "yyyy-MM-dd HH:mm:ss"
     * @return 字符串时间
     */
    public static String getDateTime(long time, String type) {
        long t = time < 100 * 10000 * 10000L ? time * 1000L : time;
        return getDateTime(new Date(t), type);
    }

    /**
     * 字符串时间转Date
     *
     * @param date 字符串时间
     * @param type 时间格式 "yyyy-MM-dd HH:mm:ss"
     * @return Date
     */
    public static Date stringToDateTime(String date, String type) {
        if (date == null) return null;
        String pattern = type == null ? "yyyy-MM-dd HH:mm:ss" : type;
        try {
            DateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            return df.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
