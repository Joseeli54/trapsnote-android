package com.exam.sid.aplicacion.model;

/**
 * Created by Elias Barrientos on 12/28/2017.
 */

public class ErrorPojoClass {
         //////////////////////////////////////////////////
        // Esta clase se encarga de leer todos          //
       // los mensajes de error que mande el servicio  //
      // al momento del usuario escribir un dato que  //
     // no cumple las validaciones de la aplicacion  //
    //////////////////////////////////////////////////
    private Integer code; // Este es el codigo que sale cuando el usuario 
                          //que se desea agregar ya existe, es decir, esta duplicado.
    private Integer index; // Este igualmente (Devuelve un 0) aparece cuando el usuario existe.
    private String errmsg; // Este es el mensaje que te avisa cuando el usuario es duplicado.
    private String message; /* 
                            * Este es el mensaje que te avisa cuando no se escribe un campo, 
                            * Se escribe un dato que no es valido
                            * Cuando se intenta ingresar un usuario menor de edad
                            * Cuando se escribe mas de 50 caracateres en nombre y apellido
                            * etc...
                            */

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getIndex() {
        return index;
    }

    public String getErrmsg() {
        return errmsg;
    }

    @Override
    public String toString() {
        return "ErrorPojoClass{" +
                "code=" + code +
                ", index=" + index +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
