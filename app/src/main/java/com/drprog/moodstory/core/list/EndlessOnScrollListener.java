package com.drprog.moodstory.core.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Use this listener to implement lazy loading
 * Created by roman.donchenko on 18.01.2016
 */
//TODO: Investigate Nikolay's approach to do this
public class EndlessOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int DEFAULT_VISIBLE_THRESHOLD = 5;

    private final OnLoadMoreListener mMoreListener;

    private int visibleThreshold; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public interface OnLoadMoreListener {
        void onLoadMore();
        boolean isLoading();
    }


    public EndlessOnScrollListener(@NonNull OnLoadMoreListener listener) {
        this(listener, DEFAULT_VISIBLE_THRESHOLD);
    }

    public EndlessOnScrollListener(@NonNull OnLoadMoreListener listener, int visibleThreshold) {
        mMoreListener = listener;
        this.visibleThreshold = visibleThreshold;
    }

    public void setRecyclerView(final RecyclerView _recyclerView) {
        mRecyclerView = _recyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (recyclerView == null) recyclerView = mRecyclerView;
        super.onScrolled(recyclerView, dx, dy);

        if (dy < 0) return;

        getLayoutManager(recyclerView);
        if (mLayoutManager == null) return;

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if (!mMoreListener.isLoading()
                && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

            // End has been reached
            mMoreListener.onLoadMore();

        }
    }

    private void getLayoutManager(RecyclerView _recyclerView) {
        if (mLayoutManager == null) {
            final RecyclerView.LayoutManager lm = _recyclerView.getLayoutManager();
            if (lm instanceof LinearLayoutManager) mLayoutManager = (LinearLayoutManager) lm;
        }
    }
}