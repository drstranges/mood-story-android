package com.drprog.moodstory.model.persisted;

import android.support.annotation.IntRange;

import com.drprog.moodstory.core.time.DateTimeUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TrackPoint extends RealmObject {

    public static final int MIN_VALUE = -5;
    public static final int MAX_VALUE = 5;

    private Long id;
    private int value;
    @PrimaryKey
    private long timestamp;
    private String country;
    private String region;
    private Double lat;
    private Double lon;
    private long lastModified;

    public TrackPoint() {
    }

    public TrackPoint(int value) {

        this.value = value;
        this.timestamp = DateTimeUtils.toUTC(System.currentTimeMillis());
        this.lastModified = this.timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(@IntRange(from = MIN_VALUE, to = MAX_VALUE) int value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}