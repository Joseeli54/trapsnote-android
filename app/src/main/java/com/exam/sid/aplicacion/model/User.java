package com.exam.sid.aplicacion.model;

public class User{

    Integer id;
    String nombre;
    String email;
    String password;

    public User(String nombre, String email, String password){
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Integer getId(){
        return id;
    }

}
