package com.exam.sid.aplicacion.remote;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Elias Barrientos on 12/25/2017.
 */

public class Validation {
	    /////////////////////////////////////////////
	   // Esta clase se encarga de validar o no,  //
	  // los atributos de los objetos de dominio //
	 //            de la aplicacion.            //
    /////////////////////////////////////////////

    public int LoginNoNulo(TextView email, TextView password){
     int Logica = 0;
        if (!TextUtils.isEmpty(email.getText().toString())
                && !TextUtils.isEmpty(password.getText().toString())) {            /////////////////////////////////
            Logica = 1;                                                           // Aqui se encarga de validar  //
        }                                                                        // Los campos de email y pass- //
        else{                                                                   // word con la variable Logica,//
            if (!TextUtils.isEmpty(email.getText().toString())                 // Si los campos estan ahi     //
                    && TextUtils.isEmpty(password.getText().toString())) {    // Te envia como respuesta un  //
                Logica = 2;                                                  // numero 1, , de lo contrario //
            }                                                               // Te devuelve 2,3 o 4.        //
            else if (TextUtils.isEmpty(email.getText().toString())         /////////////////////////////////
                    && !TextUtils.isEmpty(password.getText().toString())) {
                Logica = 3;
            }
            else{
                Logica = 4;
            }
        }

        return Logica;
        }

    public boolean Registerfull(TextView username, TextView name,
                                TextView last_name, TextView email, TextView password){
        return !TextUtils.isEmpty(username.getText().toString()) &&         ///////////////////////////////////////
                !TextUtils.isEmpty(name.getText().toString()) &&           // Aqui verifica si todos los campos //
                !TextUtils.isEmpty(last_name.getText().toString()) &&     // De registro estan escritos.       //
                !TextUtils.isEmpty(email.getText().toString()) &&        ///////////////////////////////////////
                !TextUtils.isEmpty(password.getText().toString());
    }

    public boolean Textfull(TextView text){                         ///////////////////////////////
           return !TextUtils.isEmpty(text.getText().toString());   //    Aqui verifica si       //
                                                                  // una variable en especifico// 
                                                                 //       Esta escrita.       // 
    }                                                           ///////////////////////////////

    public boolean FechaNacfull(TextView dia, TextView mes, TextView year){  /////////////////////////////
        return  !TextUtils.isEmpty(dia.getText().toString()) &&             // Aqui se verifica si los //
                !TextUtils.isEmpty(mes.getText().toString()) &&            // Campos de fecha estan   //
                !TextUtils.isEmpty(year.getText().toString());            //        escritos.        //
    }                                                                    /////////////////////////////

    public void campos_de_Login(TextView email, TextView password){ // Solo para Login
        if(Textfull(email)){
            email.setBackgroundColor(0x8cbfe2e2);
            email.setHintTextColor(0xab000000);                   ////////////////////////////////////
        }                                                        // Aqui se verifican los campos   //
        else{                                                   // Uno por uno que no esten escri //
            email.setBackgroundColor(0xb6ef9c97);              // tos y se colorean de rojo tanto//
            email.setHintTextColor(Color.RED);                // fondo, como el texto.          //
        }                                                    ////////////////////////////////////

        if(Textfull(password)){
            password.setBackgroundColor(0x8cbfe2e2);
            password.setHintTextColor(0xab000000);
        }
        else{
            password.setBackgroundColor(0xb6ef9c97);
            password.setHintTextColor(Color.RED);
        }
    }

    public void campos_de_textos(TextView username, TextView name,
                                TextView last_name, TextView email, TextView password){
        // Solo para Register
        if(Textfull(username)){
            username.setBackgroundColor(0x8cbfe2e2);
            username.setHintTextColor(0xab000000);
        }else{
            username.setBackgroundColor(0xb6ef9c97);
            username.setHintTextColor(Color.RED);       ////////////////////////////////////////////////////
        }                                              // Aqui se verifican los campos de register       //
        if(Textfull(name)){                           // Uno por uno, para ver si estan bien escritos   //
            name.setBackgroundColor(0x8cbfe2e2);     // Sino, se colorea el fondo de rojo, junto con   //
            name.setHintTextColor(0xab000000);      //               el texto.                        //
        }                                          ////////////////////////////////////////////////////
        else{
            name.setBackgroundColor(0xb6ef9c97);
            name.setHintTextColor(Color.RED);
        }

        if(Textfull(last_name)){
            last_name.setBackgroundColor(0x8cbfe2e2);
            last_name.setHintTextColor(0xab000000);
        }
        else{
            last_name.setBackgroundColor(0xb6ef9c97);
            last_name.setHintTextColor(Color.RED);
        }

        if(Textfull(email)){
            email.setBackgroundColor(0x8cbfe2e2);
            email.setHintTextColor(0xab000000);
        }
        else{
            email.setBackgroundColor(0xb6ef9c97);
            email.setHintTextColor(Color.RED);
        }

        if(Textfull(password)){
            password.setBackgroundColor(0x8cbfe2e2);
            password.setHintTextColor(0xab000000);
        }
        else{
            password.setBackgroundColor(0xb6ef9c97);
            password.setHintTextColor(Color.RED);
        }
    }

    public void campos_de_fecha(TextView dia, TextView mes, TextView year){ // Solo para fecha
        if(Textfull(dia)){
            dia.setBackgroundColor(0x8cbfe2e2);
            dia.setHintTextColor(0xab000000);
        }else{
            dia.setBackgroundColor(0xb6ef9c97);    /////////////////////////////////////////////////////
            dia.setHintTextColor(Color.RED);      // Esta tiene la misma funcion de campos_de_Login  //
        }                                        // Y campos_de_Register, solo que es aplicada      //
                                                //                 a las fechas.                   //
        if(Textfull(mes)){                     /////////////////////////////////////////////////////
            mes.setBackgroundColor(0x8cbfe2e2);
            mes.setHintTextColor(0xab000000);
        }
        else{
            mes.setBackgroundColor(0xb6ef9c97);
            mes.setHintTextColor(Color.RED);
        }

        if(Textfull(year)){
            year.setBackgroundColor(0x8cbfe2e2);
            year.setHintTextColor(0xab000000);
        }
        else{
            year.setBackgroundColor(0xb6ef9c97);
            year.setHintTextColor(Color.RED);
        }
    }

    public boolean fecha_apropiada(TextView dia, TextView mes){
        boolean validado = false;
        if(dia_correcto(Integer.parseInt(dia.getText().toString())) &&
                mes_correcto(Integer.parseInt(mes.getText().toString()))){   //////////////////////////////////////
            validado = true;                                                // Aqui se verifica si la fecha     //
        }                                                                  // Esta dentro del formato          //
        return validado;                                                  // dd/mm/yyyy, solo verificamos     //
    }                                                                    // dia y mes, las funciones         //
                                                                        // dia_correcto y mes_correcto      //
    public boolean dia_correcto(int dia){                              // Se encargan de determinar si     //
        return (dia > 0)&&(dia <= 31);                                // el dia o el mes estan dentro     //
    }                                                                //           de ese formato.        //
                                                                    //////////////////////////////////////
    public boolean mes_correcto(int mes){
        return (mes > 0)&&(mes <= 12);
    }

}