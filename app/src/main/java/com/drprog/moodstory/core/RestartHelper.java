package com.drprog.moodstory.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created on 07.03.2016.
 */
public abstract class RestartHelper implements Application.ActivityLifecycleCallbacks {

    private final AtomicBoolean mIsAppRestarting = new AtomicBoolean(false);
    private Class<? extends Activity> mLastCreatedActivity;

    public RestartHelper(android.app.Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    public synchronized void requestAppRestart() {
        if (mIsAppRestarting.get() || isRestartScreen(mLastCreatedActivity)) return;
        LogHelper.LOGD("Application restarting...");
        mIsAppRestarting.set(true);
        launchStartActivity();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mLastCreatedActivity = activity.getClass();
        if (isRestartScreen(mLastCreatedActivity)) {
            mIsAppRestarting.set(false);
            LogHelper.LOGD("Application started");
        }
    }


    public abstract boolean isRestartScreen(Class<? extends Activity> activityClass);
    public abstract void launchStartActivity();

    @Override public void onActivityStarted(Activity activity) {}

    @Override public void onActivityResumed(Activity activity) {}

    @Override public void onActivityPaused(Activity activity) {}

    @Override public void onActivityStopped(Activity activity) {}

    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override public void onActivityDestroyed(Activity activity) {}
}
