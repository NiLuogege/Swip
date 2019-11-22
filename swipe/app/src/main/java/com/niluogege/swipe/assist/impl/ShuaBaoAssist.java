package com.niluogege.swipe.assist.impl;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.niluogege.swipe.assist.Assist;

import java.util.Date;
import java.util.List;

/**
 * Created by niluogege on 2019/11/22.
 */
public class ShuaBaoAssist extends Assist {
    public static final String PACKAGE_SHUA_BAO = "com.jm.video";

    private boolean isStart = false;//是否已启动

    @Override
    public void execute(AccessibilityService service, AccessibilityEvent event) {
        if (service != null) {
            CharSequence name = event.getPackageName();
            if (TextUtils.equals(PACKAGE_SHUA_BAO, name)) {
                AccessibilityNodeInfo root = service.getRootInActiveWindow();
                if (root != null) {
                    final List<AccessibilityNodeInfo> lists = root.findAccessibilityNodeInfosByViewId("com.jm.video:id/list");

                    if (lists != null && lists.size() > 0) {
                        if (!isStart) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true) {
                                        long currentTimeMillis = new Date().getTime();

                                        if (currentTimeMillis > nextTime) {

                                            lists.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

                                            int delayTime = (int) (Math.random() * (25 - 10) + 10) * 1000;

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
}
