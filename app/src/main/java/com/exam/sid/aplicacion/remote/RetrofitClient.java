package com.exam.sid.aplicacion.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient { /* Esta clase creará un semifallo Retrofit en el método getClient(String baseUrl)
                                 y lo devolverá al llamador. */

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()) //construir la instancia
                    .build(); //También estamos especificando el convertidor JSON que necesitamos (Gson)
        }
        return retrofit;
    }
}
