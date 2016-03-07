package com.drprog.moodstory.view.navigation;

import android.app.Activity;

import com.drprog.moodstory.view.MainActivity;

import java.util.EnumMap;

/**
 * Created by roman.donchenko on 12.02.2016.
 */
public class ScreenConfig {

    public enum NavScreen {
        NONE,
        MAIN
    }

    public enum NavSection {
        NONE
//        ,MAIN_EVENTS

    }


    public enum NavTab {
        NONE
//        ,MAIN_FRIENDS_ALL,
//        MAIN_FRIENDS_INCOMING_REQUESTS,
//        MAIN_FRIENDS_OUTCOMING_REQUESTS
    }

    public static final EnumMap<NavScreen, Class<? extends Activity>> SCREENS;

    static {
        SCREENS = new EnumMap<>(NavScreen.class);
        SCREENS.put(NavScreen.MAIN, MainActivity.class);
    }

}
