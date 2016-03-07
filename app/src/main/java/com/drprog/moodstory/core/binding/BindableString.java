package com.drprog.moodstory.core.binding;

import android.databinding.BaseObservable;

/**
 * Two way bindable string
 * Created by roman.donchenko on 18.01.2016
 */
public class BindableString extends BaseObservable {
    String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}