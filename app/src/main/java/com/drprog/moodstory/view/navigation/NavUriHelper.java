package com.drprog.moodstory.view.navigation;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.drprog.moodstory.view.navigation.ScreenConfig.*;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roman.donchenko on 12.02.2016.
 */
public class NavUriHelper {
    public static final String EXTRA_NAVIGATION_URI = "EXTRA_NAVIGATION_URI";

    public static final String SCHEME_NAVIGATION = "navigate";
    public static final String AUTHORITY_NAVIGATION = "open";
    public static final String EMPTY_PATH = "empty";
    public static final int INDEX_SEGMENT_SCREEN = 0;
    public static final int INDEX_SEGMENT_SECTION = 1;
    public static final int INDEX_SEGMENT_TAB = 2;


    public static boolean isNavigationUri(String uriLink) {
        if (TextUtils.isEmpty(uriLink)) return false;
        try {
            Uri uri = Uri.parse(uriLink);
            if (SCHEME_NAVIGATION.equals(uri.getScheme())) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getNavScreenName(Uri uriLink) {
        return getPathSegment(uriLink, INDEX_SEGMENT_SCREEN);
    }

    public static NavScreen getNavScreen(Uri uriLink) {
        String screenName = getNavScreenName(uriLink);
        try {
            return NavScreen.valueOf(screenName);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return NavScreen.NONE;
    }

    public static NavSection getNavSection(Uri uriLink) {
        String sectionName = getNavSectionName(uriLink);
        try {
            return NavSection.valueOf(sectionName);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return NavSection.NONE;
    }

    public static String getNavSectionName(Uri uriLink) {
        return getPathSegment(uriLink, INDEX_SEGMENT_SECTION);
    }

    public static String getNavTabName(Uri uriLink) {
        return getPathSegment(uriLink, INDEX_SEGMENT_TAB);
    }


    public static NavTab getNavTab(Uri uriLink) {
        String tabName = getNavTabName(uriLink);
        try {
            return NavTab.valueOf(tabName);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return NavTab.NONE;
    }

    private static String getPathSegment(Uri uri, int segment) {
        try {
            return uri.getPathSegments().get(segment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EMPTY_PATH;
    }

    public static class UriBuilder {
        private final String mScreenName;
        private String mSectionName;
        private String mTabName;
        private Map<String, String> params;

        public UriBuilder(@NonNull NavScreen navScreen) {
            this(navScreen.name());
        }

        public UriBuilder(@NonNull String screenName) {
            if (TextUtils.isEmpty(screenName))
                throw new InvalidParameterException("screenName can't be empty");
            mScreenName = screenName;
            params = new HashMap<>();
        }

        public UriBuilder appendSection(NavSection navSection) {
            appendSection(navSection.name());
            return this;
        }

        public UriBuilder appendTab(NavTab navTab) {
            appendTab(navTab.name());
            return this;
        }

        public UriBuilder appendSection(String sectionName) {
            mSectionName = sectionName;
            return this;
        }

        public UriBuilder appendTab(String tabName) {
            mTabName = tabName;
            return this;
        }

        public UriBuilder appendParameter(@NonNull String key, @NonNull String value) {
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) return this;
            params.put(key, value);
            return this;
        }

        private void prepareToBuild() {
            if (TextUtils.isEmpty(mSectionName)) mSectionName = EMPTY_PATH;
            if (TextUtils.isEmpty(mTabName)) mTabName = EMPTY_PATH;
        }

        public Uri build() {
            prepareToBuild();

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(SCHEME_NAVIGATION);
            uriBuilder.authority(AUTHORITY_NAVIGATION);
            uriBuilder.appendPath(mScreenName);
            uriBuilder.appendPath(mSectionName);
            uriBuilder.appendPath(mTabName);
            for (String key : params.keySet()) {
                uriBuilder.appendQueryParameter(key, params.get(key));
            }
            return uriBuilder.build();
        }

    }
}
