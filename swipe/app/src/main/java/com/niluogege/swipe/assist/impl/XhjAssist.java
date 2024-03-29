package com.niluogege.swipe.assist.impl;

import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.niluogege.swipe.SwipeService;
import com.niluogege.swipe.assist.Assist;

import java.util.Date;
import java.util.List;

/**
 * Created by niluogege on 2019/11/22.
 */
public class XhjAssist extends Assist {
    private static final String PACKAGE_XHJ = "com.aihuishou.airent";


    private SwipeService mService;

    public XhjAssist(SwipeService mService) {
        this.mService = mService;
    }

    @Override
    public void execute(AccessibilityEvent event) {
        if (mService != null) {
            CharSequence name = event.getPackageName();
            if (TextUtils.equals(PACKAGE_XHJ, name)) {
                AccessibilityNodeInfo root = mService.getRootInActiveWindow();
                if (root != null) {
                    List<AccessibilityNodeInfo> viewPagers = root.findAccessibilityNodeInfosByViewId("com.aihuishou.airent:id/vp");
                    if (viewPagers != null && viewPagers.size() >= 2) {

                        long currentTimeMillis = new Date().getTime();

                        if (currentTimeMillis > nextTime) {

                            viewPagers.get(1).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

                            int delayTime = (int) (Math.random() * (25 - 10) + 10) * 1000;

                            Log.e("XhjAssist", "delayTime:" + delayTime);
                            nextTime = currentTimeMillis + delayTime;
                        }
                    }
                }
            }
        }
    }
}
