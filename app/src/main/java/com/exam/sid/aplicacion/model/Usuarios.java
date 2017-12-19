package com.exam.sid.aplicacion.model;

public class Usuarios {

    private String id;
    private String username;
    private String name;
    private String last_name;
    private String email;
    private Integer v;

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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
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