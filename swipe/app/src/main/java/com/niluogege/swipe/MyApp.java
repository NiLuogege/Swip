package com.niluogege.swipe;

import android.app.Application;

import com.niluogege.swipe.utils.LogUtil;

/**
 * Created by niluogege on 2019/11/23.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.setLogSwitch(BuildConfig.DEBUG);
    }
}
