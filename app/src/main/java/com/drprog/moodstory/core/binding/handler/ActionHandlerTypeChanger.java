package com.drprog.moodstory.core.binding.handler;

import android.view.View;

/**
 * Created by roman.donchenko on 07.03.2016
 */
public interface ActionHandlerTypeChanger {
    /**
     * Called at start of action processing. There you can change actionType before handling.
     * @param view           view, which was clicked
     * @param actionType     action type
     * @param model          model
     * @return new action type to handle
     */
    String onClickActionChangeType(View view, String actionType, Model model);

}
