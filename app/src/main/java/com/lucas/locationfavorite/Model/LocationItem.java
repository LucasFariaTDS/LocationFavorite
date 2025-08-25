package com.lucas.locationfavorite.Model;

public class LocationItem {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String photoUri;

    public LocationItem(int id, String name, double latitude, double longitude, String photoUri) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoUri = photoUri;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getPhotoUri() { return photoUri; }
}
