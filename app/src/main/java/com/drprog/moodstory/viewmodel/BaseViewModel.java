package com.drprog.moodstory.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import io.realm.Realm;

public abstract class BaseViewModel implements ViewModel {

    private Context mContext;
    private Realm realm;

    public BaseViewModel(Context context, Bundle savedInstanceState) {
        this.mContext = context;
        realm = Realm.getDefaultInstance();
    }

    protected Realm getRealm() {
        return realm;
    }

    @CallSuper
    @Override
    public void onDestroy() {
        realm.close();
        realm = null;
        mContext = null;
    }

    public void onSaveInstanceState(Bundle outState) {

    }

    protected Context getContext() {
        return mContext;
    }

    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }
}
