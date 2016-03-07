package com.drprog.moodstory.view.callback;

/**
 * Created by roman.donchenko on 29.02.2016.
 */
public interface BackPressHandler {
    /**
     * @return Return false to allow normal back processing to proceed, true to consume it here.
     */
    boolean onBackPressed();
}
