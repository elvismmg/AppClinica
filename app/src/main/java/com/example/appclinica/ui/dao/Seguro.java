package com.example.appclinica.ui.dao;

public class Seguro {

    private String IdEmpresaSeguro;
    private String Nombre;
    private String Copago;

    public Seguro(String idEmpresaSeguro, String nombre, String copago) {
        IdEmpresaSeguro = idEmpresaSeguro;
        Nombre = nombre;
        Copago = copago;
    }

    public String getIdEmpresaSeguro() {
        return IdEmpresaSeguro;
    }

    public void setIdEmpresaSeguro(String idEmpresaSeguro) {
        IdEmpresaSeguro = idEmpresaSeguro;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCopago() {
        return Copago;
    }

    public void setCopago(String copago) {
        Copago = copago;
    }
}
