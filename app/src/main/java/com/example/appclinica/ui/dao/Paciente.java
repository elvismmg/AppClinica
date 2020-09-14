package com.example.appclinica.ui.dao;

public class Paciente {

    private String idPaciente;
    private String codTipo;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String sexo;
    private String fechaNacimiento;
    private String telefono;
    private String correo;
    private String tipoSangre;
    private String peso;
    private String altura;
    private String fotoUrl;
    private String clave;
    private String activo;

    public Paciente(String idPaciente, String codTipo, String numeroDocumento, String nombres, String apellidos, String sexo, String fechaNacimiento, String telefono, String correo, String tipoSangre, String peso, String altura, String fotoUrl, String clave, String activo) {
        this.idPaciente = idPaciente;
        this.codTipo = codTipo;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.tipoSangre = tipoSangre;
        this.peso = peso;
        this.altura = altura;
        this.fotoUrl = fotoUrl;
        this.clave = clave;
        this.activo = activo;
    }

    public Paciente(){};

    public Paciente(String idPaciente, String codTipo, String numeroDocumento, String nombres, String apellidos, String sexo, String fechaNacimiento, String tipoSangre, String peso, String altura) {
        this.idPaciente = idPaciente;
        this.codTipo = codTipo;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
        this.peso = peso;
        this.altura = altura;

    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
