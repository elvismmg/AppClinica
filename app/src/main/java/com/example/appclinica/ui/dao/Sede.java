package com.example.appclinica.ui.dao;

public class Sede {

    private String IdSede;
    private String Nombre;
    private String Direccion;
    private String Distrito;
    private double UbicacionLatitud;
    private double UbicacionLongitud;
    private String FotoUrl;

    public Sede(String idSede, String nombre, String direccion, String distrito, double ubicacionLatitud, double ubicacionLongitud, String fotoUrl) {
        IdSede = idSede;
        Nombre = nombre;
        Direccion = direccion;
        Distrito = distrito;
        UbicacionLatitud = ubicacionLatitud;
        UbicacionLongitud = ubicacionLongitud;
        FotoUrl = fotoUrl;
    }

    public String getIdSede() {
        return IdSede;
    }

    public void setIdSede(String idSede) {
        IdSede = idSede;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public double getUbicacionLatitud() {
        return UbicacionLatitud;
    }

    public void setUbicacionLatitud(double ubicacionLatitud) {
        UbicacionLatitud = ubicacionLatitud;
    }

    public double getUbicacionLongitud() {
        return UbicacionLongitud;
    }

    public void setUbicacionLongitud(double ubicacionLongitud) {
        UbicacionLongitud = ubicacionLongitud;
    }

    public String getFotoUrl() {
        return FotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        FotoUrl = fotoUrl;
    }
}
