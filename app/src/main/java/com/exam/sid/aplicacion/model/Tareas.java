package com.exam.sid.aplicacion.model;

/**
 * Created by Elias Barrientos on 12/18/2017.
 */

public class Tareas {

    String _id;
    String categoria;
    String descripcion;
    String fechaRegistro;
    boolean completado;

    public Tareas(String categoria, String descripcion) {
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
