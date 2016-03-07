package com.drprog.moodstory.core.binding.handler;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by roman.donchenko on 18.01.2016
 */
public interface Action<M extends Model> {
    /**
     * @param view
     * @param actionType
     * @param model
     */
    void onActionFire(@Nullable View view, @Nullable String actionType, @Nullable M model);

    boolean isModelAccepted(Model model);
}
