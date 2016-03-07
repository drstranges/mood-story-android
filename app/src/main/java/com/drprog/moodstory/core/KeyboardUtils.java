package com.drprog.moodstory.core;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Helper class for hiding keyboard
 * Created by roman.donchenko on 18.01.2016
 */
public class KeyboardUtils {
    public static void hideSoftKeyboard(final Activity _activity) {
        if(_activity != null && _activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) _activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(_activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
