package com.exam.sid.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Post;
import com.exam.sid.aplicacion.model.Usuarios;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity{

    private Usuarios usuario;
    private TextView mResponseTv;
    private Button btn_Update;
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final EditText email = (EditText) findViewById(R.id.login_email);
        final EditText password = (EditText) findViewById(R.id.login_password);

        btn_Update = (Button) findViewById(R.id.to_update);
        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnRegister = (Button) findViewById(R.id.register_button);
        Button btnGetDelete = (Button) findViewById(R.id.delete_button);
        Button btnGetList = (Button) findViewById(R.id.search_button);
        Button btnSignIn = (Button) findViewById(R.id.signin_button);

        mResponseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResponse("");
                tocoResponse();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showResponse("Cargando...");
                Post post = new Post(email.getText().toString(),
                        password.getText().toString());
                sendLogin(post, email, password);
            }
        });

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = username.getText().toString();
                getList(nick);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mover_a_Registro();
            }
        });

        btnGetDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nick = username.getText().toString();
                deleteUser(nick);
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mover_a_Actualizacion();
            }
        });

    }

    private void sendLogin(Post post, final TextView email, final TextView password){

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        UserClient client = retrofit.create(UserClient.class);

        Call<Post> listCall = client.postLogin(post);
        listCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.code() == 200){
                    String token = response.headers().get("x-auth");
                    if(token != null){
                        showResponse("Activo");
                        mover_a_Tareas(token, response.body().getName()+" "+
                                response.body().getLastName());
                        DeleteDate(email,password);
                    }
                }else if(response.code() == 400){
                    showResponse("Correo/Contraseña incorrecto");
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                showResponse("Problem Connection");
            }
        });


    }

    private void getList(String username) {

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        UserClient client = retrofit.create(UserClient.class);

        Call<Get> listCall = client.getList(username);

        listCall.enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if (response.isSuccessful()) {
                    usuario = response.body().getUsuario();
                    if (usuario != null) {
                        showResponse(usuario.getName()+" "+usuario.getLast_name());
                        showUpdate();
                    }
                }
            }

            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                Log.e(getApplicationContext().toString(), t.getMessage());
            }
        });

    }

    public void deleteUser(String username){
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        UserClient client = retrofit.create(UserClient.class);

        Call<Get> listCall = client.Deleteuser(username);

        listCall.enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()){
                    usuario = response.body().getUsuario();
                    showResponse("Usuario Eliminado: " + usuario.getName()+" "+usuario.getLast_name());
                }
            }

            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                Log.e(getApplicationContext().toString(), t.getMessage());
            }
        });
    }

    public void tocoResponse(){
        if(mResponseTv.getVisibility() == View.VISIBLE) {
            mResponseTv.setVisibility(View.GONE);
        }
    }

    public void showResponse(String response) { //aqui hago visible el aviso de mensaje
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }

    public void showUpdate() { //aqui hago visible el aviso de mensaje
        if(btn_Update.getVisibility() == View.GONE) {
            btn_Update.setVisibility(View.VISIBLE);
        }
    }

    public void DeleteDate(TextView email, TextView password){
        email.setText("");
        password.setText("");
    }

    public void mover_a_Registro() {
        Intent ListSong = new Intent(getApplicationContext(), Register.class);
        startActivity(ListSong);
    }

    public void mover_a_Tareas(String token, String name) {
        Intent ListSong = new Intent(getApplicationContext(), Task.class);
        ListSong.putExtra("variable_string", token);
        ListSong.putExtra("variable_name", name);
        startActivity(ListSong);
    }

    public void mover_a_Actualizacion() {
        Intent ListSong = new Intent(getApplicationContext(), Update.class);
        startActivity(ListSong);
    }

}
