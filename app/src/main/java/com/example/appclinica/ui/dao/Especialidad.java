package com.example.appclinica.ui.dao;

public class Especialidad {

    private String IdEspecialidad;
    private String Nombre;
    private String descripcion;

    public Especialidad(String idEspecialidad, String nombre, String descripcion) {
        IdEspecialidad = idEspecialidad;
        Nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getIdEspecialidad() {
        return IdEspecialidad;
    }

    public void setIdEspecialidad(String idEspecialidad) {
        IdEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
