package com.niluogege.swipe.assist;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by niluogege on 2019/11/22.
 */
public interface Assist {
    void execute(AccessibilityService service, AccessibilityEvent event);
}
