package com.example.appclinica.ui.dao;

public class HorarioMedico {

    private String idProgramacionAtencion;
    private String fecha;
    private String hora;
    private String medico;
    private String nombreMedico;
    private String especialidad;
    private String especialidad_t;
    private String consultorio;
    private String consultorio_t;
    private String sede;

    public HorarioMedico(String idProgramacionAtencion, String fecha, String hora, String medico,
                         String nombreMedico, String especialidad, String especialidad_t, String consultorio,
                         String consultorio_t, String sede) {
        this.idProgramacionAtencion = idProgramacionAtencion;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.especialidad_t = especialidad_t;
        this.consultorio = consultorio;
        this.consultorio_t = consultorio_t;
        this.sede = sede;
    }

    public String getIdProgramacionAtencion() {
        return idProgramacionAtencion;
    }

    public void setIdProgramacionAtencion(String idProgramacionAtencion) {
        this.idProgramacionAtencion = idProgramacionAtencion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEspecialidad_t() {
        return especialidad_t;
    }

    public void setEspecialidad_t(String especialidad_t) {
        this.especialidad_t = especialidad_t;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getConsultorio_t() {
        return consultorio_t;
    }

    public void setConsultorio_t(String consultorio_t) {
        this.consultorio_t = consultorio_t;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
