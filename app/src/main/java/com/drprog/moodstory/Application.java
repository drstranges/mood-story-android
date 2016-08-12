package com.drprog.moodstory;


import android.app.Activity;

import com.drprog.moodstory.core.RestartHelper;
import com.drprog.moodstory.view.MainActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Application extends android.app.Application {

    private static Application INSTANCE;
    private RestartHelper mRestartHelper;

    public static Application getInstance() {
        return INSTANCE;
    }

    public void restartApplication() {
        mRestartHelper.requestAppRestart();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        init();
    }

    private void init() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        //Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);

        mRestartHelper = new RestartHelper(this){
            @Override
            public boolean isRestartScreen(Class<? extends Activity> activityClass) {
                return MainActivity.class.getName().equals(activityClass.getName());
            }

            @Override
            public void launchStartActivity() {
                MainActivity.getIntent(getApplicationContext());
            }
        };


    }
}
