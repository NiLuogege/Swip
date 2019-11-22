package com.niluogege.swipe.assist;

import android.view.accessibility.AccessibilityEvent;

/**
 * Created by niluogege on 2019/11/22.
 */
public abstract class Assist {
    protected long nextTime;

    public abstract void execute(AccessibilityEvent event);
}
