package com.exam.sid.aplicacion.model;

public class Usuarios {

    private String id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                "name='"+ name + '\''+
                ", last_name='"+ last_name + '\''+
                ", email='"+ email+ '\''+
                ", password="+ password +
                '}';
    }

}
