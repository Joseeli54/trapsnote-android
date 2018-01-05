package com.exam.sid.aplicacion.model;

/**
 * Created by bosie on 17/12/17.
 */

public class Tareas {
        ///////////////////////////////////////////////////
       // Esta clase se encarga de guardar los datos    //
      // de tareas que se van a enviar y a guardar.    //
     // Y tiene sus respectivos getter y setter       //
    ///////////////////////////////////////////////////

    private String nombre; //Nombre de la tarea

    private String _id; /*Toda tarea tiene su Id, esta se devuelve una vez
                          se crea la tarea del usuario*/
    private String descripcion; //Descripcion de actividad o tarea que tenga el usuario
    private Boolean completado; /*Esta variable booleana te avisa si la tarea se completo.
                                  Al principio te devuelve un false */
    private String username; //Toda tarea debe tener el username del usuario
    private String fechaRegistro; /*El servicio te devuelve la fecha en la que se registro
                                   la tarea*/
    private String categoria; //Tipo de tarea que se va a realiza

    public Tareas(String categoria, String descripcion, String nombre) { /*El sistema solo requiere que se envie
                                                            categoria, descripcion y nombre de la tarea*/
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public Tareas(boolean completado){
        this.completado = completado;
    }


    public String getNombre() {
        return nombre;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public String getCategoria() {
        return categoria;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public String getUsername_t() {
        return username;
    }

    public void setUsername_t(String username_t) {
        this.username = username_t;
    }

    @Override
    public String toString(){ // aqui se muestran los datos completos
        return "Get{"+
                "descripcion='"+ descripcion + '\''+
                ", completado='"+ completado + '\''+
                ", username='"+ username + '\''+'}';
    }
}