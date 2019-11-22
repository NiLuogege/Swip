package com.niluogege.swipe;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.niluogege.swipe.assist.impl.ShuaBaoAssist;
import com.niluogege.swipe.assist.impl.WeishiAssist;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
