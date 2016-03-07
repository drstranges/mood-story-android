package com.drprog.moodstory.core.binding.handler;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Use as parent for other Action
 * Created by roman.donchenko on 18.01.2016
 */
public abstract class BaseAction<M extends Model> implements Action<M> {
    protected List<OnActionFireListener> mActionFiredListeners = new ArrayList<>(1);

    public void addActionFiredListener(OnActionFireListener listener) {
        if (listener != null) mActionFiredListeners.add(listener);
    }

    public void removeActionFireListener(OnActionFireListener listener) {
        if (listener != null) mActionFiredListeners.remove(listener);
    }

    public void clearActionFireListeners() {
        mActionFiredListeners.clear();
    }

    public void notifyOnActionFired(View view, String actionType, Model oldModel) {
        for (OnActionFireListener listener : mActionFiredListeners) {
            listener.onClickActionFired(view, actionType, oldModel);
        }
    }
}
