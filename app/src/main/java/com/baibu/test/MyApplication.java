package com.baibu.test;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by minna_Zhou on 2016/12/25.
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler{
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        
    }
}
