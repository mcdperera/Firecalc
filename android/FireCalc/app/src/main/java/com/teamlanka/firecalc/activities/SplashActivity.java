package com.teamlanka.firecalc.activities;

import android.content.Context;
import android.os.Bundle;

import com.teamlanka.firecalc.R;
import com.teamlanka.firecalc.Utils.DelayUtils;
import com.teamlanka.firecalc.customactivity.FullScreenActivity;

public class SplashActivity extends FullScreenActivity {
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        DelayUtils.delay(3, new DelayUtils.DelayCallback() {
            @Override
            public void afterDelay() {
                MainActivity.StartIntent(context);
                finish();
            }
        });

    }
}
