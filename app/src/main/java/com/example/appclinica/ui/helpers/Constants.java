package com.example.appclinica.ui.helpers;

public class Constants {
    public static final String regExEmail = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    public static final String regExPhoneNumber = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

    public static final String WSUrlLoginPaciente = "http://portusalud.atwebpages.com/index.php/buscarPaciente";
    public static final String WSUrlInsertarPaciente = "http://portusalud.atwebpages.com/index.php/insertarPaciente";
    public static final String WSUrlActualizarPaciente = "http://portusalud.atwebpages.com/index.php/actualizarPaciente";
    public static final String WSUrlEliminarPaciente = "http://portusalud.atwebpages.com/index.php/eliminarPaciente";
    public static final String WSUrlGetSeguros = "http://portusalud.atwebpages.com/index.php/listarSeguros";
}
