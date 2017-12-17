package com.exam.sid.aplicacion.model;

public class Post {

    String username;
    String name;
    String last_name;
    String email;
    String password;

    public Post(String username, String name, String last_name,
                String email, String password){
        this.username = username;
        this.name = name;
        this.last_name = last_name;
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
