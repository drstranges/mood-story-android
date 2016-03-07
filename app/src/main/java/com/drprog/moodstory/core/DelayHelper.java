package com.drprog.moodstory.core;

import android.os.Handler;

/**
 * Created by roman.donchenko on 26.02.2016.
 */
public class DelayHelper {

    private Handler mHandler;

    public DelayHelper(Handler handler) {
        mHandler = handler;
    }

    public void clearAndRun(Runnable runnable, long delay) {
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, delay);
    }

    public void clearAllAndRun(Runnable runnable, long delay) {
        clearAll();
        mHandler.postDelayed(runnable, delay);
    }

    public void destroy(){
        clearAll();
        mHandler = null;
    }

    public void clearAll() {mHandler.removeCallbacksAndMessages(null);}


}
