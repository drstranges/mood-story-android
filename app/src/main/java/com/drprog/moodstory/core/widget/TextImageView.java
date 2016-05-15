package com.drprog.moodstory.core.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by donchenko_r on 14.03.2016.
 */
public class TextImageView extends FrameLayout {

    private TextView mTextView;
    private ImageView mImageView;

    public TextImageView(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        initStyledAttributes(attrs, defStyleAttr, defStyleRes);
    }

    private void initStyledAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView = new ImageView(getContext(), attrs, defStyleAttr, defStyleRes);
        } else {
            mImageView = new ImageView(getContext(), attrs, defStyleAttr);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextView = new TextView(getContext(), attrs, defStyleAttr, defStyleRes);
        } else {
            mTextView = new TextView(getContext(), attrs, defStyleAttr);
        }

        addView(mImageView, generateDefaultLayoutParams());
        addView(mTextView, generateDefaultLayoutParams());
    }

}
