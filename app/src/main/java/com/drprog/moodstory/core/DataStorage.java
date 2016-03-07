package com.drprog.moodstory.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.drprog.moodstory.Application;
import com.drprog.moodstory.model.persisted.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class DataStorage {

    private static final String DATA_STORAGE = "data_storage";
    private static final String DATA_STORAGE_PERSISTED = "data_storage_persisted";
    private static final String LOCAL_SETTINGS = "local_settings";
    private static final String USER = "user";
    private static final String TOKEN = "token";
    private static final String LAST_SYNC = "last_sync";

    private static final String NOTIFICATION_URI = "notification_sound";
    private static final String KEY_CACHED_USER_LOGIN = "KEY_CACHED_USER_LOGIN";

    public static ObservableField<User> sCurrentUser = new ObservableField<>();
    private static Gson sGson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences sPersistedSharedPreferences;
    private static SharedPreferences sLocalPreferences;
    private static String sToken;

    private static void initUser() {
        getUser();
    }

    private static SharedPreferences getPreferences() {
        if (sSharedPreferences == null) {
            sSharedPreferences = initSharedPref(DATA_STORAGE);
        }
        return sSharedPreferences;
    }

    private static SharedPreferences getPersistedPreferences() {
        if (sPersistedSharedPreferences == null) {
            sPersistedSharedPreferences = initSharedPref(DATA_STORAGE_PERSISTED);
        }
        return sPersistedSharedPreferences;
    }

    private static SharedPreferences initSharedPref(String storage) {
        return Application.getInstance().getSharedPreferences(storage, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getLocalSettings() {
        if (sLocalPreferences == null) {
            sLocalPreferences = Application.getInstance().getSharedPreferences(LOCAL_SETTINGS, Context.MODE_PRIVATE);
        }
        return sLocalPreferences;
    }

    public static void releaseStorage() {
        releaseUser();
        getPreferences().edit().clear().apply();
    }

    public static void releaseUser() {
        sCurrentUser.set(null);
        sToken = null;
    }

    public static void storeUser(final User user) {
        sCurrentUser.set(user);
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(USER, sGson.toJson(user));
        editor.apply();
        cacheUserLogin(user.email, user.phone);
    }

    private static void cacheUserLogin(String email, String phone) {
        Set<String> set = new HashSet<>(getCachedUserLogin());
        if (!set.contains(email) || !set.contains(phone)) {
            set.add(email);
            set.add(phone);
            getPersistedPreferences().edit().putStringSet(KEY_CACHED_USER_LOGIN, set).apply();
        }
    }

    @NonNull
    public static Set<String> getCachedUserLogin() {
        return getPersistedPreferences().getStringSet(KEY_CACHED_USER_LOGIN, new HashSet<String>());
    }

    public static User getUser() {
        User user = sCurrentUser.get();
        if (user == null) {
            final String userJson = getPreferences().getString(USER, null);
            if (!TextUtils.isEmpty(userJson)) {
                user = new Gson().fromJson(userJson, new TypeToken<User>() {
                }.getType());
                sCurrentUser.set(user);
            }
        }
        return user;
    }

    public static void storeToken(final String _token) {
        sToken = _token;
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(TOKEN, _token);
        editor.apply();
    }

    public static String getToken() {
        if (!TextUtils.isEmpty(sToken)) return sToken;
        sToken = getPreferences().getString(TOKEN, null);
        return sToken;
    }

}
