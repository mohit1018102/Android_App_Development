package com.technomaniacs.android.quakereport.utility.common;

public class Earthquake {

    private float magnitude;
    private String location;
    private long time;
    private String mUrl;

    public Earthquake(float magnitude, String location, long time, String url)
    {
        this.magnitude=magnitude;
        this.location=location;
        this.time=time;
        mUrl=url;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public long getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getmUrl() {
        return mUrl;
    }


}
