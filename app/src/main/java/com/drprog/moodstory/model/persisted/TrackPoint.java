package com.drprog.moodstory.model.persisted;

import android.support.annotation.IntRange;

public class TrackPoint {

    public static final int MIN_VALUE = -5;
    public static final int MAX_VALUE = 5;

    private Long id;
    private int value;
    private String country;
    private String region;
    private Double lat;
    private Double lon;
    private String timestamp;
    private String modified;

    public TrackPoint() {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}