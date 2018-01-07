package com.exam.sid.aplicacion.service;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Patch;
import com.exam.sid.aplicacion.model.Post;
import com.exam.sid.aplicacion.model.Tareas;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserClient{  ///////////////////////////////////////////////////////
                             // Esta interface se encarga de hacer las peticiones //
                            // al Rest App. Los request que se usaron fueron     //
                           // GET, POST, PATCH, PUT, DELETE                     //
                          ///////////////////////////////////////////////////////

    @POST("usuarios") //Este metodo se encarga de hacer la peticion pare crear usuarios
    Call<Post> createAccount(@Body Post post); //Se envia un body

    @POST("/login") //Este metodo se encarga de hacer la peticion para hacer el login
    Call<Post> postLogin(@Body Post post); //Se envia un body

    @GET("usuarios/{username}") //Este metodo se usa para leer un usuario
    Call<Get> getList(@Path("username") String username); //requiere una cabecera

    @DELETE("usuarios/{username}") //Elimina usuario (Este no se implementara)
    Call<Get> Deleteuser(@Path("username") String username);

    @PATCH("usuarios/{username}") //Este metodo se usa para modificar datos de usuario
    Call<Patch> sendUpdate(@Path("username") String username,
                           @Body Patch patch); //Requiere una cabecera y se manda un body

    @POST("{username}/tareas") //Este metodo que se usa para crear tarea
    Call<Tareas> sendTask(@Path("username") String username,
                          @Body Tareas tareas); //Requiere una cabecera y se manda un body

    @GET("{username}/tareas") //Este metodo te lee todas la tareas
    Call<Get> getTask(@Path("username") String username); //Solo se debe asignar la cabecera

    @DELETE("{username}/tareas/{id}") //Elimina una tarea
    Call<Get> deleteTask(@Path("username") String username,
                         @Path("id") String id); //Requiere dos cabeceras

    @PATCH("{username}/tareas/{id}") //Modifica descripcion y categorias
    Call<Tareas> updateTask(@Path("username") String username,
                         @Path("id") String id,
                         @Body Tareas tareas); //Requiere dos cabeceras

    @PUT("{username}/tareas/{id}") //Modifica solo completado
    Call<Tareas> updateComplete(@Path("username") String username,
                                @Path("id") String id,
                                @Body Tareas tareas); //Requiere dos cabeceras

    @DELETE("usuarios/logout") //Peticion para cerrar sesion
    Call<String> cerrarsesion();
}