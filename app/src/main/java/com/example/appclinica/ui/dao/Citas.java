package com.example.appclinica.ui.dao;

public class Citas {

    private String especialidad;
    private String estado;
    private String fecha;
    private String tipo;

    public Citas(String especialidad, String estado, String fecha, String tipo){
        this.especialidad = especialidad;
        this.estado  = estado;
        this.fecha   = fecha;
        this.tipo    = tipo;
    }


    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
