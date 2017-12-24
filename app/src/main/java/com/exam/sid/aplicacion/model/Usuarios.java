package com.exam.sid.aplicacion.model;

public class Usuarios {   ///////////////////////////////////////////////////
                         // Esta clase se encarga de guardar los datos    //
                        // del usuario que se va a leer. Y tiene         //
                       // sus respectivos getter y setter               //
                      ///////////////////////////////////////////////////

    private String id; //El Id del usuario
    private String username; //El username del usuario
    private String name; //El name del usuario
    private String last_name; //El last_name del usuario
    private String email; //El correo del usuario

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){ // aqui se muestran los datos completos
        return "Get{"+
                "username='"+ username + '\''+
                ", name='"+ name + '\''+
                ", last_name='"+ last_name + '\''+
                ", email='"+ email+ '\''+ '}';
    }

}