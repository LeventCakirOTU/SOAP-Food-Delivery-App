package com.fooddelivery.model;

public class Location {

    private double latitude;
    private double longitude;

    public Location() {}

    public double getLatitude()             { return latitude; }
    public void   setLatitude(double lat)   { this.latitude = lat; }

    public double getLongitude()             { return longitude; }
    public void   setLongitude(double lng)   { this.longitude = lng; }
}
