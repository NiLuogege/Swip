package com.niluogege.swipe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by niluogege on 2019/11/22.
 */
public class AppUtils {

    /**
     * 获取屏幕尺寸
     *
     * @return
     */
    public static int[] getScreenSize(Context context) {
        int[] screenSize = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        screenSize[0] = width;
        screenSize[1] = height;
        return screenSize;
    }


    /**
     * 获取NavigationBar高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * androidQ一下获取Imei
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public static String getImeiUnderQ(Context context) {
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    return telephonyManager.getDeviceId();
                }
            } catch (Exception e) {

            }
        }
        return "";
    }

    /**
     * androidQ一下获取手机号
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNum(Context context) {
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    return telephonyManager.getLine1Number();//获取本机号码  ;
                }
            } catch (Exception e) {

            }
        }
        return "";

    }

}
