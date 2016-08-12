package com.drprog.moodstory.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import io.realm.Realm;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseViewModel implements ViewModel {

    private Context mContext;
    private Realm mRealm;
    private CompositeSubscription mSubscriptions;

    public BaseViewModel(Context context, Bundle savedInstanceState, boolean initRealm) {
        this.mContext = context;
        if (initRealm) mRealm = Realm.getDefaultInstance();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mContext = null;
        unsubscribe(mSubscriptions);
        if (mRealm != null) {
            mRealm.close();
            mRealm = null;
        }
    }

    protected Realm getRealm() {
        return mRealm;
    }

    public void onSaveInstanceState(Bundle outState) {}

    protected Context getContext() {
        return mContext;
    }

    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * Use this method for add your subscription to managed CompositeSubscription,
     * which was unsubscribed automatically inside onDestroy
     * @param subscription    your subscription
     * @return the same subscription as {@param subscription}
     */
    protected Subscription manageSubscription(Subscription subscription) {
        if (mSubscriptions == null) mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(subscription);
        return subscription;
    }

    protected void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
