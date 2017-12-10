package com.exam.sid.aplicacion.model;

public class Get { // aqui se convierte el lenguaje json a java, para poder asignarle el valor y enviarlos a la url

    private Usuarios[] usuarios;

    public String getUsuarios() {
        return Conglomerado();
    }

    public Usuarios[] Datos(){
        return this.usuarios;
    }

    public void setUsuarios(Usuarios usuarios, int i) {
        this.usuarios[i] = usuarios;
    }

    public String Conglomerado(){
        String res = "";
        for (int i = 0; i < usuarios.length; i++){
            res += "[" + this.usuarios[i].getEmail() + "] \n";
        }
        return res;
    }

}