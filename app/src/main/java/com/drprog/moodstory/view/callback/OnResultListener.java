package com.drprog.moodstory.view.callback;

import android.content.Intent;

/**
 * Interface definition for a class that will be called
 * when fragment sends result to parent activity or fragment
 * <p/>
 * Created by roman.donchenko on 19.02.2016.
 */
public interface OnResultListener {
    void sendResult(int resultCode, Intent data);
}
