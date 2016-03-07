package com.drprog.moodstory.core.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for retrieving permission
 * Created by roman.donchenko on 18.01.2016
 */
public class PermissionHelper {

    public static boolean checkAnyPermissionGranted(@NonNull Context _context, String[] _permissions) {
        return _permissions.length == 0 || checkPermissions(_context, _permissions).length < _permissions.length;
    }

    @NonNull
    public static String[] checkPermissions(@NonNull Context _context, String[] _permissions) {
        if (_permissions == null) return new String[0];
        List<String> missingPermissions = new ArrayList<>(_permissions.length);
        for (String permission : _permissions) {
            if (ContextCompat.checkSelfPermission(_context, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions.toArray(new String[missingPermissions.size()]);
    }

    @NonNull
    public static void assertPermissions(@NonNull Context _context, String[] _permissions) throws PermissionSecurityException {
        if (_permissions != null) {
            List<String> missingPermissions = new ArrayList<>(_permissions.length);
            for (String permission : _permissions) {
                if (ContextCompat.checkSelfPermission(_context, permission) != PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.add(permission);
                }
            }
            int count = missingPermissions.size();
            if (count > 0){
                throw new PermissionSecurityException(missingPermissions.toArray(new String[count]),
                        "Permission is required: " + missingPermissions.toString());
            }
        }

    }

    public static boolean isSomePermissionGranted(int[] _grantResults) {
        for (int res : _grantResults){
            if (res == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPermissionGranted(@NonNull String permission, @NonNull String[] permissions, int[] grantResults) {
        int index = permission.indexOf(permission);
        return grantResults[index] == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isAnyPermissionGranted(String[] requiredPermissions, String[] permissions, int[] grantResults) {
        for (String permission : requiredPermissions) {
            if (isPermissionGranted(permission, permissions, grantResults)) return true;
        }
        return false;
    }
}
