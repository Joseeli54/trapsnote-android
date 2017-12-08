package com.exam.sid.aplicacion.remote;

import com.exam.sid.aplicacion.model.Get;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService { //peticiones

    @POST("/usuarios") //aqui se realiza la peticion post
    @FormUrlEncoded
    Call<Get> savePost(@Field("name") String name, //anteriormente era String nombre
                        @Field("last_name") String lastName, //se agrega last_name
                        @Field("email") String email,
                        @Field("password") String password);
}