package com.example.appclinica.ui.dao;

public class Citas {

    private String idCita;
    private String fecha;
    private String hora;
    private String nombreMedico;
    private String especialidad;
    private String nombrePaciente;
    private String sede;
    private String consultorio;
    private String seguro;
    private String copago;
    private String tipoCitaDesc;
    private String estado;
    private String estadoDesc;
    private String numero;
    private String imagenEvidenciaUrlA;
    private String imagenEvidenciaUrlB;
    private String imagenEvidenciaUrlC;

    public Citas(String idCita, String fecha, String hora, String nombreMedico, String especialidad,
                 String nombrePaciente, String sede, String consultorio, String seguro,
                 String copago, String tipoCitaDesc, String estado, String estadoDesc, String numero,
                 String imagenEvidenciaUrlA, String imagenEvidenciaUrlB, String imagenEvidenciaUrlC){
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.nombrePaciente = nombrePaciente;
        this.sede = sede;
        this.consultorio = consultorio;
        this.seguro = seguro;
        this.copago = copago;
        this.tipoCitaDesc  = tipoCitaDesc;
        this.estado   = estado;
        this.estadoDesc   = estadoDesc;
        this.numero    = numero;
        this.imagenEvidenciaUrlA    = imagenEvidenciaUrlA;
        this.imagenEvidenciaUrlB    = imagenEvidenciaUrlB;
        this.imagenEvidenciaUrlC    = imagenEvidenciaUrlC;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
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

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getCopago() {
        return copago;
    }

    public void setCopago(String copago) {
        this.copago = copago;
    }

    public String getTipoCitaDesc() {
        return tipoCitaDesc;
    }

    public void setTipoCitaDesc(String tipoCitaDesc) {
        this.tipoCitaDesc = tipoCitaDesc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoDesc() {
        return estadoDesc;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImagenEvidenciaUrlA() {
        return imagenEvidenciaUrlA;
    }

    public void setImagenEvidenciaUrlA(String imagenEvidenciaUrlA) {
        this.imagenEvidenciaUrlA = imagenEvidenciaUrlA;
    }

    public String getImagenEvidenciaUrlB() {
        return imagenEvidenciaUrlB;
    }

    public void setImagenEvidenciaUrlB(String imagenEvidenciaUrlB) {
        this.imagenEvidenciaUrlB = imagenEvidenciaUrlB;
    }

    public String getImagenEvidenciaUrlC() {
        return imagenEvidenciaUrlC;
    }

    public void setImagenEvidenciaUrlC(String imagenEvidenciaUrlC) {
        this.imagenEvidenciaUrlC = imagenEvidenciaUrlC;
    }
}
