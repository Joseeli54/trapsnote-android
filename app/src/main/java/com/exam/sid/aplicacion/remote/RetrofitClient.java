package com.exam.sid.aplicacion.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elias Barrientos on 12/25/2017.
 */

public class RetrofitClient {
    /////////////////////////////////////////////////////////////////
   // Retrofit cliente te facilita el manejo del codigo, ya que   //
  // Se encarga de instanciar la variable de tipo retrofit       //
 //       y adapta el Url base que se utilizara                 //
/////////////////////////////////////////////////////////////////
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit =                                                             //////////////////////////
                    new Retrofit.Builder()                                        //  Aqui se manda el    //
                            .baseUrl(baseUrl)                                    //  URL verifica y      //
                            .addConverterFactory(GsonConverterFactory.create()) // se convierte en Json //
                            .build();                                          // cada dato que este   //
                                                                              //     dentro de el.    //
                                                                             /////////////////////////
        }
        return retrofit;
    }
}
