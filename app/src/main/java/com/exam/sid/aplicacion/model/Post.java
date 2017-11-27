package com.exam.sid.aplicacion.model;

/**
 * Created by Elias Barrientos on 11/25/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post { // aqui se convierte el lenguaje json a java, para poder asignarle el valor y enviarlos a la url

    //esto se hizo gracias al sitio web jsonschema2pojo

    @SerializedName("name") //en la url anterior se llamaba "nombre", no "name"
    @Expose
    private String name;
    @SerializedName("last_name") /*este es el elemento que se agrega*/
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    //get y setter de los elementos

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){ // aqui se muestran los datos completos
        return "Post{"+
               "name='"+ name + '\''+
                ", last_name='"+ lastName + '\''+
                ", email='"+ email+ '\''+
                ", password="+ password +
                '}';
    }
}