/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Changed by roman.donchenko
 */
package com.drprog.moodstory.core;

import android.support.annotation.NonNull;
import android.util.Log;

import com.drprog.moodstory.BuildConfig;
import com.drprog.moodstory.Config;


@SuppressWarnings({"unused", "PointlessBooleanExpression"})
public class LogHelper {
    private static final String LOG_PREFIX = "ms_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 22;
    public static final boolean LOG_ALL_ENABLED = BuildConfig.DEBUG || Config.IS_DOGFOOD_BUILD;
    private static final java.lang.String TAG_DEFAULT = LOG_PREFIX + "default";

    /**
     * Use as "private static final String TAG = makeLogTag("GCMs");"
     * @param str string, which with will be used to build a tag
     * @return log tag
     */
    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }



    public static void LOGD(String message) {
        LOGD(TAG_DEFAULT,message);
    }

    public static void LOGD(final String tag, @NonNull String message) {
        if (LOG_ALL_ENABLED || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public static void LOGD(final String tag, @NonNull String message, Throwable cause) {
        if (LOG_ALL_ENABLED || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message, cause);
        }
    }

    public static void LOGV(final String tag, @NonNull String message) {
        if (LOG_ALL_ENABLED && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void LOGV(final String tag, @NonNull String message, Throwable cause) {
        if (LOG_ALL_ENABLED && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void LOGI(final String tag, @NonNull String message) {
        Log.i(tag, message);
    }

    public static void LOGI(final String tag, @NonNull String message, Throwable cause) {
        Log.i(tag, message, cause);
    }

    public static void LOGW(final String tag, @NonNull String message) {
        Log.w(tag, message);
    }

    public static void LOGW(final String tag, @NonNull String message, Throwable cause) {
        Log.w(tag, message, cause);
    }

    public static void LOGE(final String tag, @NonNull String message) {
        Log.e(tag, message);
    }

    public static void LOGE(final String tag, @NonNull String message, Throwable cause) {
        Log.e(tag, message, cause);
    }

    private LogHelper() {
    }
}
