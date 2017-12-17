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
import com.exam.sid.aplicacion.model.Usuarios;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity{

    private Usuarios[] usuarios;
    private TextView mResponseTv;
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText email = (EditText) findViewById(R.id.login_email);
        final EditText password = (EditText) findViewById(R.id.login_password);

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnRegister = (Button) findViewById(R.id.register_button);
        Button btnGetList = (Button) findViewById(R.id.signin_button);

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = email.getText().toString();
                String clave = password.getText().toString();
                getList(correo, clave);
            }
        });



    }

    private void getList(final String email, final String password) {

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        UserClient client = retrofit.create(UserClient.class);

        Call<Get> listCall = client.getList();

        listCall.enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                usuarios = response.body().Datos();
                int Valido = 0;

                for(int i = 0; i<usuarios.length; i++)
                    if(password.equals(usuarios[i].getPassword())
                            && email.equals(usuarios[i].getEmail())) {
                        mover_a_Tareas();
                        Valido = 1;
                    }

                 if(Valido == 0){
                     showResponse("Usuario No Reconocido");
                 }
            }

            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                Log.e(getApplicationContext().toString(), t.getMessage());
            }
        });

    }

    public void showResponse(String response) { //aqui hago visible el aviso de mensaje
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }



    public void mover_a_Tareas() {
        Intent ListSong = new Intent(getApplicationContext(), Task.class);
        startActivity(ListSong);
    }



}

