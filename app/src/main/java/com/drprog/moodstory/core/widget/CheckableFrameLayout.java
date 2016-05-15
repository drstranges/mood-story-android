package com.drprog.moodstory.core.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.view.ViewDebug;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.drprog.moodstory.core.widget.util.CheckedChangeHelper;


/**
 * Created by roman.donchenko on 18.02.2016.
 */
@BindingMethods({
        @BindingMethod(type = CheckableFrameLayout.class,
                attribute = "android:checked",
                method = "setCheckedSoft"),
})

public class CheckableFrameLayout extends FrameLayout implements Checkable {

    private CheckedChangeHelper mHelper;

    public CheckableFrameLayout(Context context) {
        this(context, null);
    }

    public CheckableFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CheckedChangeHelper.CHECKED_STATE_SET);
        }
        return drawableState;
    }


    @Override
    public boolean performClick() {
        toggle();

        final boolean handled = super.performClick();
        if (!handled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            playSoundEffect(SoundEffectConstants.CLICK);
        }

        return handled;
    }

    @Override
    public void toggle() {
        getCheckHelper().toggle(this);
    }

    private CheckedChangeHelper getCheckHelper() {
        if (mHelper == null) mHelper = new CheckedChangeHelper();
        return mHelper;
    }

    @Override
    @ViewDebug.ExportedProperty
    public boolean isChecked() {
        return getCheckHelper().isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        setChecked(checked, true);
    }

    public void setCheckedSoft(boolean checked) {
        setChecked(checked, false);
    }

    /**
     * <p>Changes the checked state of this button.</p>
     *
     * @param checked true to check the button, false to uncheck it
     * @param notify  true to call listeners
     */
    public void setChecked(boolean checked, boolean notify) {
        if (getCheckHelper().setChecked(checked)) {
            refreshDrawableState();

            if (notify) getCheckHelper().notifyCheckedChanged(this);
        }
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(CheckedChangeHelper.OnItemCheckedChangeListener listener) {
        getCheckHelper().setOnCheckedChangeListener(listener);
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes. This callback is used for internal purpose only.
     *
     * @param listener the callback to call on checked state change
     * @hide
     */
    void setOnCheckedChangeWidgetListener(CheckedChangeHelper.OnItemCheckedChangeListener listener) {
        getCheckHelper().setOnCheckedChangeWidgetListener(listener);
    }

    public Long getItemId() {
        return getCheckHelper().getItemId();
    }

    public void setItemId(Long itemId) {
        getCheckHelper().setItemId(itemId);
    }
}