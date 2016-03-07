package com.drprog.moodstory.core.binding.handler.action;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

import com.drprog.moodstory.core.binding.handler.BaseAction;
import com.drprog.moodstory.core.binding.handler.Model;

/**
 * Use for all action based on Intent
 * Created by roman.donchenko on 18.01.2016
 */
public abstract class IntentAction<M extends Model> extends BaseAction<M> {

    @Override
    public void onActionFire(View view, String actionType, M model) {
        Context context = view.getContext();
        final Intent intent = getIntent(context, actionType, model);
        if (intent == null) return;
        if (Build.VERSION.SDK_INT >= 21) {
            ActivityOptions activityOptions = prepareTransition(intent, view);
            if (activityOptions != null) {
                context.startActivity(intent, activityOptions.toBundle());
                return;
            }
        }
        notifyOnActionFired(view, actionType, model);
        context.startActivity(intent);
    }

    protected abstract @Nullable Intent getIntent(Context context, String actionType, M model);

    protected ActivityOptions prepareTransition(Intent intent, View view) {
        return null;
    };
}
