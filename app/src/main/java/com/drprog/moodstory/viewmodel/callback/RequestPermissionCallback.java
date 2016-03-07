package com.drprog.moodstory.viewmodel.callback;

/**
 * Created by roman.donchenko on 23.02.2016.
 */
public interface RequestPermissionCallback {
    void requestPermission(String[] _requiredPermissions, String _message);
}
