package com.drprog.moodstory.core.binding.handler;

import android.view.View;

public interface ActionClickListener {
    void onActionClick(final View view, final String actionType, final Model model);
}
