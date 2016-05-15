package com.drprog.moodstory.core.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.drprog.moodstory.R;

/**
 * Created by donchenko_r on 14.03.2016.
 */
public class SquareImageView extends ImageView {
    private int mSidePriority;

    public SquareImageView(Context context) {
        super(context);
        init(null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initStyledAttributes(attrs);
    }

    private void initStyledAttributes(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SquareView, 0, 0);
        mSidePriority = a.getInt(R.styleable.SquareView_sidePriority, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        final int imageSize;
        switch (mSidePriority) {
            case 0: //min
                if (widthSpecMode != MeasureSpec.EXACTLY && width == 0) width = height;
                if (heightSpecMode != MeasureSpec.EXACTLY && height == 0) height = width;

                imageSize = (width < height) ? width : height;
                break;
            case 1: //width
                imageSize = width;
                break;
            case 2: //height
            default:
                imageSize = height;
                break;
        }
        setMeasuredDimension(imageSize, imageSize);
    }
}
