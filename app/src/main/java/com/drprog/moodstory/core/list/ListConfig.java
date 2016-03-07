package com.drprog.moodstory.core.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class for simple configuring Adapter for RecyclerView
 * Created by roman.donchenko on 18.01.2016
 */
public class ListConfig {
    private final RecyclerView.Adapter mAdapter;
    private final LayoutManagerProvider mLayoutManagerProvider;
    private final List<RecyclerView.ItemDecoration> mItemDecorations;
    private final List<RecyclerView.OnScrollListener> mScrollListeners;
    private final boolean mHasFixedSize;

    public ListConfig(RecyclerView.Adapter adapter, LayoutManagerProvider layoutManagerProvider, List<RecyclerView.ItemDecoration> itemDecorations, List<RecyclerView.OnScrollListener> scrollListeners, boolean hasFixedSize) {
        mAdapter = adapter;
        mLayoutManagerProvider = layoutManagerProvider;
        mItemDecorations = itemDecorations != null
                ? itemDecorations : Collections.<RecyclerView.ItemDecoration>emptyList();
        mScrollListeners = scrollListeners != null
                ? scrollListeners : Collections.<RecyclerView.OnScrollListener>emptyList();
        mHasFixedSize = hasFixedSize;
    }

    public void applyConfig(RecyclerView recyclerView) {
        final RecyclerView.Adapter adapter = mAdapter;
        final RecyclerView.LayoutManager layoutManager;
        final Context context = recyclerView.getContext();
        if (adapter == null || mLayoutManagerProvider == null || (layoutManager = mLayoutManagerProvider.get(context)) == null) return;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(mHasFixedSize);
        recyclerView.setAdapter(adapter);
        for (RecyclerView.ItemDecoration itemDecoration : mItemDecorations) {
            recyclerView.addItemDecoration(itemDecoration);
        }
        for (RecyclerView.OnScrollListener scrollListener : mScrollListeners) {
            if (scrollListener instanceof EndlessOnScrollListener)
                ((EndlessOnScrollListener) scrollListener).setRecyclerView(recyclerView);
            recyclerView.addOnScrollListener(scrollListener);
        }
    }

    public static class Builder {
        private final RecyclerView.Adapter mAdapter;
        private LayoutManagerProvider mLayoutManagerProvider;
        private List<RecyclerView.ItemDecoration> mItemDecorations;
        private List<RecyclerView.OnScrollListener> mOnScrollListeners;
        private boolean mHasFixedSize;
        private int mDefaultDividerOffset = -1;

        public Builder(RecyclerView.Adapter _bindableAdapter) {
            mAdapter = _bindableAdapter;
        }

        public Builder setLayoutManagerProvider(LayoutManagerProvider layoutManagerProvider) {
            mLayoutManagerProvider = layoutManagerProvider;
            return this;
        }

        public Builder addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
            if (mItemDecorations == null) {
                mItemDecorations = new ArrayList<>();
            }
            mItemDecorations.add(itemDecoration);
            return this;
        }

        public Builder addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
            if (mOnScrollListeners == null) {
                mOnScrollListeners = new ArrayList<>();
            }
            mOnScrollListeners.add(onScrollListener);
            return this;
        }

        public Builder setHasFixedSize(boolean isFixedSize) {
            mHasFixedSize = isFixedSize;
            return this;
        }

        public Builder setDefaultDividerEnabled(boolean enabled) {
            mDefaultDividerOffset = enabled ? 0 : -1;
            return this;
        }

        public Builder setDefaultDividerOffset(int offset) {
            mDefaultDividerOffset = offset;
            return this;
        }

        public ListConfig build(Context context) {
            if (mLayoutManagerProvider == null) mLayoutManagerProvider = new SimpleLinearLayoutManagerProvider();
            if (mDefaultDividerOffset >= 0)
                addItemDecoration(new DividerItemDecoration(mDefaultDividerOffset));
            return new ListConfig(
                    mAdapter,
                    mLayoutManagerProvider,
                    mItemDecorations,
                    mOnScrollListeners,
                    mHasFixedSize);
        }
    }

    public interface LayoutManagerProvider {
        RecyclerView.LayoutManager get(Context context);
    }


    public static class SimpleLinearLayoutManagerProvider implements LayoutManagerProvider {
        @Override
        public RecyclerView.LayoutManager get(Context context) {
            return new LinearLayoutManager(context);
        }
    }
}

