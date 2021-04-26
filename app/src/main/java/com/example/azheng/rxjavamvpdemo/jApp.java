package com.example.azheng.rxjavamvpdemo;

import android.app.Application;

/**
 * Created by jincancan on 2018/3/15.
 * Description:
 */

public class jApp extends Application {

    private static jApp instance;

    public static jApp getIns(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
