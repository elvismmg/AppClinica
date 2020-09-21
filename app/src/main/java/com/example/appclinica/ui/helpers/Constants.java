package com.example.appclinica.ui.helpers;

public class Constants {
    public static final String regExEmail = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    public static final String regExPhoneNumber = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

    public static final String WSUrlLoginPaciente = "http://portusalud.atwebpages.com/index.php/buscarPaciente";
    public static final String WSUrlBuscarPacientePorId = "http://portusalud.atwebpages.com/index.php/buscarPacientePorId";
    public static final String WSUrlInsertarPaciente = "http://portusalud.atwebpages.com/index.php/insertarPaciente";
    public static final String WSUrlActualizarPaciente = "http://portusalud.atwebpages.com/index.php/actualizarPaciente";
    public static final String WSUrlEliminarPaciente = "http://portusalud.atwebpages.com/index.php/eliminarPaciente";
    public static final String WSUrlGetSeguros = "http://portusalud.atwebpages.com/index.php/listarSeguros";
    public static final String WSUrlGetSedes = "http://portusalud.atwebpages.com/index.php/listarSedes";
    public static final String WSUrlGetCitas = "http://portusalud.atwebpages.com/index.php/buscarCitasPorPaciente";
    public static final String WSUrlAnularCita = "http://portusalud.atwebpages.com/index.php/anularCita";
    public static final String WSUrlDatosMaestros = "http://portusalud.atwebpages.com/index.php/datosMaestros";
    public static final String WSUrlGetEspecilidades = "http://portusalud.atwebpages.com/index.php/listarEspecialidades";
    public static final String WSUrlGetMedicos1 = "http://portusalud.atwebpages.com/index.php/listarMedicos1";
    public static final String WSUrlGetMedicos2 = "http://portusalud.atwebpages.com/index.php/listarMedicos2";
    public static final String WSUrlGetProgramacion = "http://portusalud.atwebpages.com/index.php/listarProgramacion";
    public static final String WSUrlInsertarCita = "http://portusalud.atwebpages.com/index.php/insertarCita0";
}
