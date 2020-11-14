package com.example.crud_android_firebase.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Persona {

    private String uid;
    private String Nombre;
    private String Correo;
    private String Apellidos;
    private String Password;


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



    public Persona() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
