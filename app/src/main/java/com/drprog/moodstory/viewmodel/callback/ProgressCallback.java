package com.drprog.moodstory.viewmodel.callback;

/**
 * Interface for throwing progress callbacks from ViewModel
 * Created by roman.donchenko on 05.02.2016.
 */
public interface ProgressCallback {
    void showProgress(String message);
    void hideProgress();
}
