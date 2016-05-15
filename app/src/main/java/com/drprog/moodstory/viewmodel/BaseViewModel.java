package com.drprog.moodstory.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;

public abstract class BaseViewModel implements ViewModel {

    private Context mContext;

    public BaseViewModel(Context context, Bundle savedInstanceState) {
        this.mContext = context;
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mContext = null;
    }

    public void onSaveInstanceState(Bundle outState) {}

    protected Context getContext() {
        return mContext;
    }

    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }
}
