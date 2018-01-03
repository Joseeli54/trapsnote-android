package com.exam.sid.aplicacion.model;

import java.util.Date;

public class Post {   ///////////////////////////////////////////////////
                     // Esta clase se encarga de guardar los datos    //
                    // de usuarios que se van a enviar. Y tiene      //
                   // sus respectivos getter y setter               //
                  ///////////////////////////////////////////////////

    String username; //Es obligatorio el username
    String name; //Se envia el nombre de usuario
    String last_name; //Se envia el segundo nombre
    String email; //Se envia el correo
    String password; //Se envia la contraseña para usarse en el login
    Date fechaDeNacimiento; //Se envia la fechaNac para despues usarse
    String formaRegistro; // Se debe tener una forma en la que se registro
    // Obviamente para este proyecto es por medio del movil

    public Post(String username, String name, String last_name,
                String email, String password, Date fecha, String formaRegistro){ //Constructor de datos, para crear el usuario
        this.username = username;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.fechaDeNacimiento = fecha;
        this.formaRegistro = formaRegistro;
    }

    public Post(String email, String password){ //Constructor de email y password, para enviarlos al login
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
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

}
