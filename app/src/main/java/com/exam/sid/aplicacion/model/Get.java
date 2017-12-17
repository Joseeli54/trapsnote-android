package com.exam.sid.aplicacion.model;

public class Get { // aqui se convierte el lenguaje json a java, para poder asignarle el valor y enviarlos a la url

    private Usuarios usuario;

    public Usuarios getUsuario(){
        return this.usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

}