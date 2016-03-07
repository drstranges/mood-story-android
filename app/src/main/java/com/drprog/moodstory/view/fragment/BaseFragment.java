package com.drprog.moodstory.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Integer layoutResId = getLayoutResId();
        if (layoutResId == null) return null;
        rootView = inflater.inflate(layoutResId, container, false);
        onCreateView(rootView, savedInstanceState);
        setHasOptionsMenu(getMenuResId() != null);
        final Integer titleResId = getActionBarTitleResId();
        setActionBarTitle(titleResId);
        return rootView;
    }

    /**
     *
     * @return Layout resource id or null for no layout
     */
    @Nullable
    @LayoutRes
    protected abstract Integer getLayoutResId();

    /**
     *
     * @return Toolbar title resource id or null for no title
     */
    protected abstract Integer getActionBarTitleResId();

    /**
     *
     * @return menu resource id or null for no menu
     */
    @Nullable
    @MenuRes
    protected Integer getMenuResId(){
        return 0;
    }

    protected abstract void onCreateView(@Nullable View _rootView, @Nullable Bundle _savedInstanceState);

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final Integer menuResId = getMenuResId();
        if (menuResId != null) {
            inflater.inflate(menuResId, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    public ActionBar getActionBar() {
        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            return ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        return null;
    }


    private void setActionBarTitle(@Nullable @StringRes Integer _titleResId) {
        if (_titleResId == null) return;
        ActionBar actionBar = getActionBar();
         if (actionBar != null){
            actionBar.setTitle(_titleResId);
        }
    }
}
