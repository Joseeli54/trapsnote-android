package com.exam.sid.aplicacion.service;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Elias Barrientos on 11/26/2017.
 */

public interface UserClient{

    @POST("usuarios")
    Call<User> createAccount(@Body User user);

    @GET("/usuarios")
    Call<Get> getList();

}
