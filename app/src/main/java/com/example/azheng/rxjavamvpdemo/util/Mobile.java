package com.example.azheng.rxjavamvpdemo.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Mobile {

    public static String mobilePhoneReplace(String phone) {

        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    public static String mobilePhone(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }
    public static String getString(String s, String s1)//s是需要删除某个子串的字符串s1是需要删除的子串
    {
        int postion = s.indexOf(s1);
        int length = s1.length();
        int Length = s.length();
        String newString = s.substring(0,postion) + s.substring(postion + length, Length);
        return newString;//返回已经删除好的字符串
    }

}
