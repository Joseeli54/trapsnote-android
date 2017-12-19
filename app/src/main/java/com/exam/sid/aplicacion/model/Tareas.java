package com.exam.sid.aplicacion.model;

/**
 * Created by bosie on 17/12/17.
 */

public class Tareas {

    private String descripcion;
    private Boolean completado;
    private String username_t;

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
        return username_t;
    }

    public void setUsername_t(String username_t) {
        this.username_t = username_t;
    }

    @Override
    public String toString(){ // aqui se muestran los datos completos
        return "Get{"+
                "descripcion='"+ descripcion + '\''+
                ", completado='"+ completado + '\''+
                ", username='"+ username_t + '\''+'}';
    }
}
