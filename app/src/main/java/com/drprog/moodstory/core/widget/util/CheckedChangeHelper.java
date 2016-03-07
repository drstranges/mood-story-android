package com.drprog.moodstory.core.widget.util;

import android.widget.Checkable;

/**
 * Created by roman.donchenko on 18.02.2016.
 */
public class CheckedChangeHelper {
    public static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked = false;

    private Long mItemId; //Used in list to identify which element was checked

    private OnItemCheckedChangeListener mOnCheckedChangeListener;
    private OnItemCheckedChangeListener mOnCheckedChangeWidgetListener;
    private boolean mBroadcasting;

    public void toggle(final Checkable view) {
        setChecked(!mChecked);
        notifyCheckedChanged(view);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public boolean setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            return true;
        }
        return false;
    }

    public void notifyCheckedChanged(final Checkable view) {
        // Avoid infinite recursions if setChecked() is called from a listener
        if (mBroadcasting) {
            return;
        }

        mBroadcasting = true;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(view, mChecked, mItemId);
        }
        if (mOnCheckedChangeWidgetListener != null) {
            mOnCheckedChangeWidgetListener.onCheckedChanged(view, mChecked, mItemId);
        }

        mBroadcasting = false;
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnItemCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes. This callback is used for internal purpose only.
     *
     * @param listener the callback to call on checked state change
     * @hide
     */
    public void setOnCheckedChangeWidgetListener(OnItemCheckedChangeListener listener) {
        mOnCheckedChangeWidgetListener = listener;
    }

    public Long getItemId() {
        return mItemId;
    }

    public void setItemId(Long itemId) {
        this.mItemId = itemId;
    }

    /**
     * Interface definition for a callback to be invoked when the checked state
     * of a compound button changed.
     */
    public static interface OnItemCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param view    The compound button view whose state has changed.
         * @param checked The new checked state of buttonView.
         * @param itemId  ItemId if has been set.
         */
        void onCheckedChanged(Checkable view, boolean checked, Long itemId);
    }
}
