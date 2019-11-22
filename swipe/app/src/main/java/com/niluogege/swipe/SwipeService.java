package com.niluogege.swipe;

import android.accessibilityservice.AccessibilityService;
import android.os.SystemClock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by niluogege on 2019/11/19.
 */
public class SwipeService extends AccessibilityService {

    public static SwipeService mService;
    private boolean flag = false;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        EventBus.getDefault().register(this);
        mService = this;
        Log.e("SwipeService", "onServiceConnected");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("SwipeService", "onDestroy");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event != null) {
            Log.d("SwipeService", "action:" + event.toString());


            AccessibilityNodeInfo root = getRootInActiveWindow();

            List<AccessibilityNodeInfo> viewPagers = root.findAccessibilityNodeInfosByViewId("com.aihuishou.airent:id/vp");

            Log.e("SwipeService", "viewPagers.size():" + viewPagers.size());
            if (viewPagers.size() > 0) {


                viewPagers.get(1).performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
            }

        }
    }

    @Override
    public void onInterrupt() {
        Log.e("SwipeService", "onInterrupt");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(String event) {
        Log.e("SwipeService", "event= " + event);
        switch (event) {
            case "start_shuabao":
                flag = true;
                run_shuabao();
                break;

            case "start_weishi":
                flag = true;
                run_weishi();
                break;

            case "start_test":
                test();
                break;

            case "stop":
                flag = false;
                break;

            default:
                break;
        }
    }


    private void test() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AccessibilityNodeInfo root = getRootInActiveWindow();
                List<AccessibilityNodeInfo> viewPagers = root.findAccessibilityNodeInfosByViewId("com.jm.video:id/list");
                List<AccessibilityNodeInfo> viewPagers2 = root.findAccessibilityNodeInfosByViewId("com.jm.video:id/mmViewPager");
                Log.e("SwipeService", "viewPagers.size():" + viewPagers.size() + " viewPagers2=" + viewPagers2.size());
                if (viewPagers.size() > 0) {
                    viewPagers.get(0).performAction(AccessibilityNodeInfo.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
                }
            }
        }).start();

    }


    private void run_shuabao() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    String command = "input swipe 550 1450 550 700";
                    execShellCmd(command);
                    int random = (int) (Math.random() * (25 - 10) + 10) * 1000;
                    Log.e("SwipeService", "刷宝执行了一次= " + random);

                    SystemClock.sleep(random);

                }
            }
        }).start();


    }

    private void run_weishi() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    String command = "input swipe 550 1450 550 700";
                    execShellCmd(command);
                    int random = (int) (Math.random() * (10 - 5) + 5) * 1000;
                    Log.e("SwipeService", "微视执行了一次= " + random);

                    SystemClock.sleep(random);
                }
            }
        }).start();


    }


    /**
     * 辅助功能是否启动
     */
    public static boolean isStart() {
        return mService != null;
    }


    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
//            process.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
