package com.drprog.moodstory.core.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class TopScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLayoutManager;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //check if the first item in the list is visible, and the apptoolbarlayout is also visible
        initLayoutManager(recyclerView);
        if (mLayoutManager != null) {
            if (recyclerView.getChildCount() == 0 ||
                    (mLayoutManager.findFirstVisibleItemPosition() == 0
                            && recyclerView.getChildAt(0).getTop() == 0
                            && isHeaderCompletelyExpanded())) {
                onScrolled(true);
            } else {
                onScrolled(false);
            }
        }
    }

    protected abstract boolean isHeaderCompletelyExpanded();

    protected abstract void onScrolled(boolean _isOnTop);

    private void initLayoutManager(RecyclerView _recyclerView) {
        if (mLayoutManager == null) {
            final RecyclerView.LayoutManager lm = _recyclerView.getLayoutManager();
            if (lm instanceof LinearLayoutManager) mLayoutManager = (LinearLayoutManager) lm;
        }
    }
}