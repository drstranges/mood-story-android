package com.drprog.moodstory.core.binding;

import android.databinding.BaseObservable;

/**
 * Two way bindable boolean
 * Created by roman.donchenko on 18.01.2016
 */
public class BindableBoolean extends BaseObservable {
    boolean value;

    public boolean get() {
        return value;
    }

    public void set(boolean _value) {
        if (this.value != _value) {
            this.value = _value;
            notifyChange();
        }
    }

}