package com.exam.sid.aplicacion.model;

/**
 * Created by Elias Barrientos on 12/15/2017.
 */

public class Patch {   ///////////////////////////////////////////////////
                      // Esta clase se encarga de guardar los datos    //
                     //   de usuarios que se van a modificar. Y tiene //
                    // sus respectivos getter y setter               //
                   ///////////////////////////////////////////////////

    String username; //Este no se modifica solo se tiene para identificar al usuario
    String name; //La variable name se cambia
    String last_name; //La variable last_name se cambia

    public Patch(String name, String last_name){ //constructor de name y last_name
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