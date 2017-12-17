package com.exam.sid.aplicacion.service;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Patch;
import com.exam.sid.aplicacion.model.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Elias Barrientos on 11/26/2017.
 */

public interface UserClient{

    @POST("usuarios")
    Call<Post> createAccount(@Body Post post);

    @GET("usuarios/{username}")
    Call<Get> getList(@Path("username") String username);

    @DELETE("usuarios/{username}")
    Call<Get> Deleteuser(@Path("username") String username);

    @PATCH("usuarios/{username}")
    Call<Patch> sendUpdate(@Path("username") String username,
                           @Body Patch patch);

}
