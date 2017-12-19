package com.exam.sid.aplicacion.model;

/**
 * Created by Elias Barrientos on 12/15/2017.
 */

public class Patch {

    String username;
    String name;
    String last_name;
    String email;

    public Patch(String name, String last_name){
        this.name = name;
        this.last_name = last_name;
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

}