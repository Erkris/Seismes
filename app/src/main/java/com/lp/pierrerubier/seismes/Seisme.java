package com.lp.pierrerubier.seismes;

/**
 * Created by pierrerubier on 10/11/2014.
 */
public class Seisme {

    private int id;
    private String title;
    private String place;
    private int time;
    private String url;
    private double magnitude;
    private double latitude;
    private double longitude;

    public Seisme(int id, String title, String place, int time, String url, double magnitude, double latitude, double longitude) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.time = time;
        this.url = url;
        this.magnitude = magnitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
