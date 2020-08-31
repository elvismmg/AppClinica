package com.example.appclinica.ui.models;

public class CenterModel {

    private String Name;
    private String Address;
    private String City;
    private String Latitude;
    private String Longitude;

    public CenterModel(String name, String address, String city, String latitude, String longitude) {
        Name = name;
        Address = address;
        City = city;
        Latitude = latitude;
        Longitude = longitude;
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

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
