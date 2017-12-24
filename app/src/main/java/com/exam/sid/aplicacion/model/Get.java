package com.exam.sid.aplicacion.model;

public class Get {        //////////////////////////////////////
                         // Esta clase guarda los objetos    //
                        //   de Usuarios y Tareas. Y tiene  //
                       // sus respectivos getter y setter  //
                      //////////////////////////////////////

    private Usuarios usuario; //Se utiliza para leer un solo usuario
    private Tareas[] tareas; //Se utiliza para leer todas las tareas
    private Tareas tarea; // Se utiliza para leer una sola tarea

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

    public Tareas getTarea() {
        return tarea;
    }

    public void setTarea(Tareas tarea) {
        this.tarea = tarea;
    }

}