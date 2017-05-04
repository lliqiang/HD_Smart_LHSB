package com.hengda.linhai.m.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.tools.StatusBarCompat;

import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.ShareSDK;

public class LaucherActivity extends BaseActivity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        ShareSDK.initSDK(this,"8f9cc1cb17028ddb7e735d6182ea265");
        StatusBarCompat.translucentStatusBar(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                openActivity(LaucherActivity.this, LanguageActivity.class);
                finish();
            }
        }, 2000);
    }
}
