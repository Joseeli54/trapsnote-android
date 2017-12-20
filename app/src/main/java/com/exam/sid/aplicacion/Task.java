package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Tareas;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elias Barrientos on 12/5/2017.
 */

public class Task extends AppCompatActivity {

    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";
    TextView mResponseTv, mWelcome;
    Button btnTask;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        Bundle datos = this.getIntent().getExtras();
        String token = datos.getString("variable_string");
        String name = datos.getString("variable_name");
        final String nick = datos.getString("variable_username");

        final TextView descripcion = (TextView) findViewById(R.id.et_descripcion);
        final TextView categoria = (TextView) findViewById(R.id.et_categoria);

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        mWelcome = (TextView) findViewById(R.id.welcome);
        verBienvenida(name);

        Button SelectGet = (Button) findViewById(R.id.Ver_tarea);
        Button SelectTask = (Button) findViewById(R.id.btn_tarea);
        btnTask = (Button) findViewById(R.id.guardar_tarea);

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWelcome("Cargando...");
                Tareas tareas = new Tareas(categoria.getText().toString(),
                                       descripcion.getText().toString());
                MostrarEnvio(descripcion, categoria);
                sendTask(nick, tareas);
            }
        });

        SelectTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarEnvio(descripcion, categoria);
            }
        });

        SelectGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWelcome("Cargando...");
                getTask(nick);
            }
        });

    }

    public void verBienvenida(String name){
        showWelcome("Bienvenido "+name);
    }

    private void sendTask(final String username, Tareas tareas){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<Tareas> call = client.sendTask(username, tareas);
        call.enqueue(new Callback<Tareas>(){
            @Override
            public void onResponse(Call<Tareas> call, Response<Tareas> response) {
                if(response.isSuccessful()){
                    showWelcome("Tarea guardada");
                }
            }

            @Override
            public void onFailure(Call<Tareas> call, Throwable t) {
                    showWelcome("No se guardo... Problema con la conexion");
            }
        });

    }

    private void getTask(String username){

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        UserClient client = retrofit.create(UserClient.class);

        Call<Get> listCall = client.getTask(username);
        listCall.enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()){
                    Tareas[] tareas = response.body().getTareas();

                    if(tareas != null){
                        String res = "";
                        for(int i = 0; i<tareas.length; i++){
                            res = res+i+") "+tareas[i].getDescripcion()+"\n\n";
                        }
                        showResponse(res);
                    }
                }
                else{
                        showWelcome("Vacio");
                }
            }
            @Override
            public void onFailure(Call<Get> call, Throwable t) {

            }
        });
    }

    public void verToken(String token){
        showResponse(token);
    }

    public void MostrarEnvio(TextView descripcion, TextView categoria){
        if(btnTask.getVisibility() == View.GONE){
            descripcion.setVisibility(View.VISIBLE);
            categoria.setVisibility(View.VISIBLE);
            btnTask.setVisibility(View.VISIBLE);
        }
        else{
            descripcion.setVisibility(View.GONE);
            categoria.setVisibility(View.GONE);
            btnTask.setVisibility(View.GONE);
        }
    }

    public void showResponse(String response) { //aqui hago visible el aviso de mensaje
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }

    public void showWelcome(String response) { //aqui hago visible el aviso de mensaje
        mWelcome.setText(response);
    }

}
