package com.example.azheng.rxjavamvpdemo.util;

import android.text.format.Time;

public class TimeUtils {

    public static String getTime() {
        Time time = new Time("GMT+8");
        time.setToNow();

        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;
        return year +
                "年 " + month +
                "月 " + day +
                "日 " + hour +
                "时 " + minute +
                "分 " + sec;

    }
}
