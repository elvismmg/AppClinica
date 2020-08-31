package com.example.appclinica.ui.dao;

public class Medico {

    private String nombre;
    private Integer imagen;

    public Medico(String nombre, Integer imagen){
        this.nombre = nombre;
        this.imagen  = imagen;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

}
