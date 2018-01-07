package com.exam.sid.aplicacion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exam.sid.aplicacion.R;
import com.exam.sid.aplicacion.service.UserClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elias Barrientos on 1/7/2018.
 */

public class Logout extends AppCompatActivity {
                /////////////////////////////////////////////////////////////////////
               // Clase encargada del cerrado de sesion, para el cual se necesita //
              // El token de autenticacion que se obtuvo al iniciar sesion.      //
             //  Sino se desea cerrar sesion, se regresa a Task                 //
            /////////////////////////////////////////////////////////////////////

    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";
    TextView mResponseTv; //Avisos de mensajes
    String token, name, username; // token, nombre y apellido
    private int peticion; // La peticion que se desea ejecutar (0 salir de la app, 1 cerrar sesion)
    private String[] descripcion = new String[1000];
    private String[] categoria = new String[1000];    ////////////////////////////////////////
    private String[] id = new String[1000];          // Arreglos de los datos de las tareas//
    private String[] nombre = new String[1000];     ////////////////////////////////////////
    private boolean[] completado = new boolean[1000];
    private String[] fechaLimit = new String[1000];
    int tamano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);

        Bundle datos = this.getIntent().getExtras();

        peticion = datos.getInt("peticion");
        token = datos.getString("variable_string");              /////////////////////////////////////
        name = datos.getString("name");                         // Adquiero los datos que fueron   //
        username = datos.getString("username");                // Suministrados por la clase Task //
        tamano = datos.getInt("tamano");                      // para no perder los datos que se //
        descripcion = datos.getStringArray("descripcion");   // obtuvieron leyendo las tareas de//
        categoria = datos.getStringArray("categoria");      //  la base de datos del usuario   //
        id = datos.getStringArray("id");                   /////////////////////////////////////
        nombre = datos.getStringArray("nombre");
        completado = datos.getBooleanArray("completado");
        fechaLimit = datos.getStringArray("fechaLimite");

        mResponseTv = (TextView) findViewById(R.id.tv_response); // Aviso de mensaje
        Button logout = (Button) findViewById(R.id.salir); // Boton de Cerrar sesion
        Button cancel = (Button) findViewById(R.id.cancel); // Boton de Cancelar

        if(peticion == 0){ // Si se desea salir de la app se quita el boton de cerrar sesion
            mResponseTv.setVisibility(View.GONE); // Se quita el mensaje de aviso
            logout.setVisibility(View.GONE); // Se quita el boton de cerrar sesion
            TextView welcome = (TextView) findViewById(R.id.welcome); // Se muestra un aviso de advertencia
            welcome.setText("Si se sale, se cerrara sesion automaticamente," +
                            " Es recomendable minimizar la aplicacion." +
                            "¿Desea salir de todas formas?");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {             /////////////////////////////////////////
                showResponse("Cargando...");            // Aqui se hace la peticion del logout //
                SendLogout();                          /////////////////////////////////////////
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ListSong = new Intent(getApplicationContext(), Task.class);
                ListSong.putExtra("variable_string", token);
                ListSong.putExtra("name", name);                       /////////////////////////////////////////
                ListSong.putExtra("username", username);              // Si se cancela, se regresan los datos//
                ListSong.putExtra("tamano", tamano);                 // Pertenecientes a Task de nuevo, como//
                ListSong.putExtra("descripcion", descripcion);      //        Si nada hubiese pasado.      //
                ListSong.putExtra("categoria", categoria);         /////////////////////////////////////////
                ListSong.putExtra("id", id);
                ListSong.putExtra("nombre", nombre);
                ListSong.putExtra("completado", completado);
                ListSong.putExtra("fechaLimite", fechaLimit);
                startActivity(ListSong);
                finish();
            }
        });
    }

    public void SendLogout(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()                ///////////////////////////////////////////
                        .addHeader("X-Auth", token)                   // Aqui se intercepta el header del token// 
                        .method(original.method(), original.body())  // Para mandarse al servicio web         //
                        .build();                                   ////////////////////////////////////////////

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Se agrega la variable client en el retrofit
                .build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<String> call = userClient.cerrarsesion(); // Se llama a cerrar sesion
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.code() == 200) {
                    showResponse("Sesion Cerrada"); // Si todo salio bien te devuelve 200
                }
                else{
                    showResponse("Sesion No Cerrada"); // Si el token no existe o 
                                                      // ya se cerro sesion te devuelve 400
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {                 /////////////////////////////////////////////////////////
                Intent ListSong = new Intent(getApplicationContext(), Main.class); // Existe un problema o falla en la aplicacion         //
                startActivity(ListSong);                                          // Una vez hecha la peticion se elimina el token       //
                finish();                                                        // Pero te devuelve un mensaje de error en onFailure   //
            }                                                                   // Por eso desde aqui se devuelve al inicio de sesion  //
        });                                                                    // No se pudo resolver este problema, aunque si elimina//
    }                                                                         // el token. Se estara verificando cual es el error    //
                                                                             /////////////////////////////////////////////////////////
    public void showResponse(String response) {
                                                        ///////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mResponseTv.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                            ///////////////////////////////////////////
        mResponseTv.setText(response);
    }

    @Override
    public void onBackPressed() {

        if(peticion == 1) { // Si la peticion es cerrar sesion, si se presiona Back, se regresa a Task
            Intent ListSong = new Intent(getApplicationContext(), Task.class);
            ListSong.putExtra("variable_string", token);
            ListSong.putExtra("name", name);
            ListSong.putExtra("username", username);
            ListSong.putExtra("tamano", tamano);
            ListSong.putExtra("descripcion", descripcion);
            ListSong.putExtra("categoria", categoria);
            ListSong.putExtra("id", id);
            ListSong.putExtra("nombre", nombre);
            ListSong.putExtra("completado", completado);
            ListSong.putExtra("fechaLimite", fechaLimit);
            startActivity(ListSong);
            finish();
        }
        else{ // Pero si se quiere salir de la aplicacion, cuando se presione Back, se saldra de la App
            finish();
        }
    }

}
