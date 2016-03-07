package com.drprog.moodstory.core.list;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.drprog.moodstory.R;

/**
 * Created by roman.donchenko on 18.01.2016
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpacing;

    public DividerItemDecoration(final int spacing) {
        mSpacing = spacing;
    }

    public DividerItemDecoration(Context _context) {
        mSpacing = _context.getResources().getDimensionPixelSize(R.dimen.loading_list_divider_size_default);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mSpacing;
    }
}
