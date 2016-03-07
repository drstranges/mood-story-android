package com.drprog.moodstory.core.binding.handler;

import android.view.View;

import com.drprog.moodstory.core.LogHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Use ActionHandler to manage action and bind them to view
 * Created by roman.donchenko on 18.01.2016
 */
public class ActionHandler implements ActionClickListener {

    private static final String LOG_TAG = LogHelper.makeLogTag("ActionHandler");

    protected Map<String, Action> mActions;
    protected ActionHandlerTypeChanger mActionHandlerTypeChanger;
    protected OnActionFireListener mOnActionFireListener;

    protected ActionHandler(Map<String, Action> actions) {
        mActions = actions;
    }

    public void setActionHandlerTypeChanger(ActionHandlerTypeChanger callback) {
        mActionHandlerTypeChanger = callback;
    }

    public void setOnActionFireListener(OnActionFireListener onActionFireListener) {
        OnActionFireListener oldListener = null;
        if (mOnActionFireListener != null) oldListener = mOnActionFireListener;
        mOnActionFireListener = onActionFireListener;
        for (Action action : mActions.values()) {
            if (action instanceof BaseAction) {
                BaseAction baseAction = ((BaseAction) action);
                baseAction.removeActionFireListener(oldListener);
                baseAction.addActionFiredListener(mOnActionFireListener);
            }
        }
    }

    public boolean canHandle(final String actionType) {
        return mActions.containsKey(actionType);
    }

    @Override
    public void onActionClick(View view, String actionType, Model model) {
        if (mActionHandlerTypeChanger != null) {
            actionType = mActionHandlerTypeChanger.onClickActionChangeType(view, actionType, model);
        }
        Action action = mActions.get(actionType);
        if (action != null && action.isModelAccepted(model)) {
            //noinspection unchecked
            action.onActionFire(view, actionType, model);
        }
    }

    public static class Builder {
        protected Map<String, Action> mActions;
        private OnActionFireListener mActionFiredListener;

        public Builder() {
            mActions = new HashMap<>();
        }

        public Builder addAction(String actionType, Action action) {
            mActions.put(actionType, action);
            return this;
        }

        public Builder setActionListener(final OnActionFireListener listener) {
            mActionFiredListener = listener;
            return this;
        }

        public ActionHandler build() {
            final ActionHandler actionHandler = new ActionHandler(mActions);
            if (mActionFiredListener != null) {
                actionHandler.setOnActionFireListener(mActionFiredListener);
            }
            return actionHandler;
        }
    }

//    public static final class SimpleBuilder extends Builder {
//
//        public SimpleBuilder addActionFollow() {
//            addAction("FOLLOW", new FollowAction());
//            return this;
//        }
//
//    }
}