package com.niluogege.swipe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.niluogege.swipe.assist.impl.KuaiShowAssist;
import com.niluogege.swipe.assist.impl.ShuaBaoAssist;
import com.niluogege.swipe.assist.impl.WeishiAssist;
import com.niluogege.swipe.utils.AppUtils;
import com.niluogege.swipe.utils.LogUtil;
import com.niluogege.swipe.utils.StringUtils;
import com.niluogege.swipe.utils.Whitelist;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(granted -> {
                        if (granted) {

                            String imei = AppUtils.getImeiUnderQ(this);
                            String phoneNum = AppUtils.getPhoneNum(this);
                            LogUtil.e("imei= " + imei + " phoneNum=" + phoneNum);

                            Whitelist whitelist = new Whitelist();
                            List<String> wl = Arrays.asList(whitelist.whiteDeivces);

                            if (StringUtils.isNotEmpty(imei)) {
                                if (wl.contains(imei)) {
                                    initView();
                                }else{
                                    toastCantUse();
                                }
                            } else if (StringUtils.isNotEmpty(phoneNum)) {
                                if (wl.contains(phoneNum)) {
                                    initView();
                                }else{
                                    toastCantUse();
                                }
                            } else {
                                initView();
                            }

                        }
                    });
        } else {
            initView();
        }


    }

    private void initView() {
        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSwipeService();
            }
        });

        findViewById(R.id.btn_start_shuabao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShuaBao();
            }
        });

        findViewById(R.id.btn_start_weishi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWeiShi();
            }
        });

        findViewById(R.id.btn_start_kuaishow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startKuaiShow();
            }
        });

        findViewById(R.id.btn_start_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("start_test");
                onBackPressed();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("stop");
            }
        });
    }

    private void startSwipeService() {
        if (!SwipeService.isStart()) {
            try {
                this.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            } catch (Exception e) {
                this.startActivity(new Intent(Settings.ACTION_SETTINGS));
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "服务已经启动了", Toast.LENGTH_LONG).show();
        }
    }

    private void startShuaBao() {
        if (SwipeService.isStart()) {

            EventBus.getDefault().post("start_shuabao");

            startActivity(getPackageManager().getLaunchIntentForPackage(ShuaBaoAssist.PACKAGE_SHUA_BAO));

            onBackPressed();
        } else {
            Toast.makeText(this, "请先启动服务", Toast.LENGTH_LONG).show();
        }
    }

    private void startWeiShi() {
        if (SwipeService.isStart()) {

            EventBus.getDefault().post("start_weishi");

            startActivity(getPackageManager().getLaunchIntentForPackage(WeishiAssist.PACKAGE_WEISHI));

            onBackPressed();
        } else {
            Toast.makeText(this, "请先启动服务", Toast.LENGTH_LONG).show();
        }
    }

    private void startKuaiShow() {
        if (SwipeService.isStart()) {

            EventBus.getDefault().post("start_kuaishow");

            startActivity(getPackageManager().getLaunchIntentForPackage(KuaiShowAssist.PACKAGE_KUAI_SHOW));

            onBackPressed();
        } else {
            Toast.makeText(this, "请先启动服务", Toast.LENGTH_LONG).show();
        }
    }

    private void toastCantUse(){
        Toast.makeText(this, "请先购买！", Toast.LENGTH_LONG).show();
    }


}
