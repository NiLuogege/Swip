package com.niluogege.swipe.assist.impl;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.niluogege.swipe.SwipeService;
import com.niluogege.swipe.assist.Assist;
import com.niluogege.swipe.utils.AppUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by niluogege on 2019/11/22.
 */
public class KuaiShowAssist extends Assist {
    public static final String PACKAGE_KUAI_SHOW = "com.kuaishou.nebula";
    private static final int START_VALUE = 5;
    private static final int END_VALUE = 10;

    private SwipeService mService;
    private boolean isStart = false;//是否已启动
    private GestureDescription.StrokeDescription mSd;
    private GestureDescription mDescription;
    private AccessibilityService.GestureResultCallback mGestureResultCallback;


    public KuaiShowAssist(SwipeService service) {
        mService = service;
        belowN();
    }

    @Override
    public void execute(AccessibilityEvent event) {
        if (mService != null) {
            CharSequence name = event.getPackageName();
            if (TextUtils.equals(PACKAGE_KUAI_SHOW, name)) {
                AccessibilityNodeInfo root = mService.getRootInActiveWindow();
                if (root != null) {
                    final List<AccessibilityNodeInfo> lists = root.findAccessibilityNodeInfosByViewId("com.kuaishou.nebula:id/slide_play_view_pager");

                    if (lists != null && lists.size() > 0) {
                        if (!isStart) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true) {
                                        long currentTimeMillis = new Date().getTime();
                                        if (currentTimeMillis > nextTime) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//api24 android7.0
                                                Log.e("ShuaBaoAssist", "7.0以上");

                                                mService.dispatchGesture(mDescription, mGestureResultCallback, null);
                                            } else {
                                                Log.e("ShuaBaoAssist", "7.0以下");

                                                lists.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                                            }


                                            int delayTime = (int) (Math.random() * (END_VALUE - START_VALUE) + START_VALUE) * 1000;

                                            Log.e("ShuaBaoAssist", "delayTime:" + delayTime);
                                            nextTime = currentTimeMillis + delayTime;
                                        }
                                    }
                                }
                            }).start();

                            isStart = true;
                        }
                    }
                }
            }
        }
    }


    private void belowN() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int[] screenSize = AppUtils.getScreenSize(mService);
            int navigationBarHeight = AppUtils.getNavigationBarHeight(mService);

            Path path = new Path();
            path.moveTo(screenSize[0] / 2, screenSize[1] - navigationBarHeight - 50);
            path.lineTo(screenSize[0] / 2, 50);

            mSd = new GestureDescription.StrokeDescription(path, 200, 600);
            mDescription = new GestureDescription.Builder().addStroke(mSd).build();
            mGestureResultCallback = new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);

                    Log.d("ShuaBaoAssist", "滑动成功");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);

                    Log.d("ShuaBaoAssist", "滑动失败");
                }
            };
        }
    }
}
