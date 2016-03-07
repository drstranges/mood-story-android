package com.drprog.moodstory.core.permission;

import android.support.annotation.NonNull;

/**
 * Custom exception used in {@link PermissionHelper}
 * Created by roman.donchenko on 18.01.2016
 */
public class PermissionSecurityException extends Throwable {
    private final String[] mRequiredPermissions;

    public PermissionSecurityException(String[] _requiredPermissions, String _message) {
        super(_message);
        mRequiredPermissions = _requiredPermissions == null ? new String[]{} : _requiredPermissions;
    }

    @NonNull
    public String[] getRequiredPermissions() {
        return mRequiredPermissions;
    }
}
