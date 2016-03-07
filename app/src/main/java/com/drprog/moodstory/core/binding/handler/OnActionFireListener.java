package com.drprog.moodstory.core.binding.handler;

import android.view.View;

/**
 * Created by roman.donchenko on 18.01.2016
 */
public interface OnActionFireListener {
    void onClickActionFired(View view, String actionType, Model model);
}
