package com.example.appclinica.ui.models;

public class CenterModel {

    private int IdCenter;
    private String Name;
    private String Address;
    private String City;
    private double Latitude;
    private double Longitude;
    private String PhotoUrl;

    public CenterModel() {
    }

    public CenterModel(int idCenter, String name, String address, String city, double latitude, double longitude) {
        Name = name;
        Address = address;
        City = city;
        Latitude = latitude;
        Longitude = longitude;
        IdCenter = idCenter;
        PhotoUrl = "";
    }

    public int getIdCenter() {
        return IdCenter;
    }

    public void setIdCenter(int idCenter) {
        IdCenter = idCenter;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }
}
