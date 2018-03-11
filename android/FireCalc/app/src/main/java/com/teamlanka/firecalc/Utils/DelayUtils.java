package com.teamlanka.firecalc.Utils;

import android.os.Handler;

/**
 * Created by Charmal on 2/14/2018.
 */

public class DelayUtils {


    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}
