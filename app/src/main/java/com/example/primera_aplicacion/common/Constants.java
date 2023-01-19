package com.example.primera_aplicacion.common;

public class Constants {

    // Constantes usadas a lo largo de la activity
    public static final String SP_USERS_KEY = "USER"; //Esta se usará como KEY de las sharedPreferences
    public static final String SP_PREFERENCES_DIRECTORY = "savedOptions"; //Esta se usará como ruta de almacenamiento de sharedPreferences
    private static final String LOGIN_USER = "admin"; //Usuario para el login en private
    private static final String LOGIN_PASS = "admin"; //Contraseña para el login en private

    // Permitimos solo acceso mediante getters.
    public static String getUser(){
        return LOGIN_USER;
    }
    public static String getPass(){
        return LOGIN_PASS;
    }
}
