package com.drprog.moodstory.viewmodel.callback.empty;

import com.drprog.moodstory.viewmodel.callback.SimpleResultViewModelCallback;

/**
 * Created by roman.donchenko on 23.02.2016.
 */
public class EmptySimpleResultViewModelCallback implements SimpleResultViewModelCallback {

    @Override
    public void requestSaveResult() {}

    @Override
    public void showError(String error) {}

    @Override
    public void showMessage(String message) {}

    @Override
    public void showProgress(String message) {}

    @Override
    public void hideProgress() {}
}
