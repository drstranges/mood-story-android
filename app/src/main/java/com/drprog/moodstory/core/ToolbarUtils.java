package com.drprog.moodstory.core;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by roman.donchenko on 23.02.2016.
 */
public class ToolbarUtils {
    public static void setStatusBarColor(Activity activity, Integer color) {
        if (activity == null || activity.isFinishing()) return;
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            if (color != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(color);
            }
        }
    }
}
