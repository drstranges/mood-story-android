package com.drprog.moodstory.viewmodel.callback;

/**
 * Simple interface for throwing progress and error callbacks from ViewModel
 * Created by roman.donchenko on 05.02.2016.
 */
public interface SimpleViewModelCallback extends ProgressCallback {
    void showError(String error);
    void showMessage(String message);
}
