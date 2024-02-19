package com.example.bloodbond;

public class Hospital {
    private String name;
    private String address;
    private String distance;
    private double latitude;
    private double longitude;

    public Hospital(String name, String address, String distance, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
