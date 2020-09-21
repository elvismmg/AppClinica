package com.example.appclinica.ui.dao;

public class Medico {

    private String codigo;
    private String nombre;
    private String especialidad;
    private String codigoConsultorio;
    private String consultorio;
    private String rutaImagen;
    private Integer imagen;


    public Medico(String codigo, String nombre, String especialidad, String codigoConsultorio, String consultorio, String rutaImagen, Integer imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.codigoConsultorio = codigoConsultorio;
        this.consultorio = consultorio;
        this.rutaImagen = rutaImagen;
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodigoConsultorio() {
        return codigoConsultorio;
    }

    public void setCodigoConsultorio(String codigoConsultorio) {
        this.codigoConsultorio = codigoConsultorio;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }
}
