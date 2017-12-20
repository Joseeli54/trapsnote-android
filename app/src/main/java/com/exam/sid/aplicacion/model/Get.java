package com.exam.sid.aplicacion.model;

public class Get { // aqui se convierte el lenguaje json a java, para poder asignarle el valor y enviarlos a la url

    private Usuarios usuario;
    private Tareas[] tareas;

    public Usuarios getUsuario(){
        return this.usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public void setTareas(Tareas[] tareas) {
        this.tareas = tareas;
    }

    public Tareas[] getTareas() {
        return tareas;
    }

}