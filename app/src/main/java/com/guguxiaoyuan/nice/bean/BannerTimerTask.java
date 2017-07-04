package com.guguxiaoyuan.nice.bean;

import android.os.Handler;

import com.guguxiaoyuan.nice.activity.MainActivity;

import java.util.TimerTask;


/**
 * Created by 萌 on 2017/3/30.
 */


    public class BannerTimerTask extends TimerTask {
        /**
         * handler
         */
        Handler handler;

        public BannerTimerTask(Handler handler) {
            super();
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendEmptyMessage(MainActivity.AUTOBANNER_CODE);
        }
    }

