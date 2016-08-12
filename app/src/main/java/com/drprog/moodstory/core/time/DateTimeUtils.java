package com.drprog.moodstory.core.time;

import java.util.TimeZone;

/**
 * Created on 15.05.2016.
 */
public class DateTimeUtils {

    private static TimeZone sTimeZone;

    public static long toUTC(long timeMillis) {
        if (sTimeZone == null) {
            sTimeZone = TimeZone.getDefault();
        }
        int utcOffset = sTimeZone.getRawOffset() + sTimeZone.getDSTSavings();
        return timeMillis + utcOffset;
    }

    public static long fromUTC(long timeMillis) {
        if (sTimeZone == null) {
            sTimeZone = TimeZone.getDefault();
        }
        int utcOffset = sTimeZone.getRawOffset() + sTimeZone.getDSTSavings();
        return timeMillis - utcOffset;
    }
}
