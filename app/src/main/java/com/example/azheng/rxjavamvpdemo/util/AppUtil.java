package com.example.azheng.rxjavamvpdemo.util;

import java.io.File;

/**
 * /**
 * Created by wsy on 2021/4/25 17:23.
 * e-mail : svipwsy@163.com
 * desc:
 */
public class AppUtil {
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    //判断文件是否存在
    public static boolean fileIsExists(File f) {
        try {

            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
