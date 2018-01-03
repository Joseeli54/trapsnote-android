package com.exam.sid.aplicacion.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.R;
import com.exam.sid.aplicacion.model.Patch;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update extends AppCompatActivity {
       /////////////////////////////////////////////////////////////////////
      // Esta es la clase donde se ejecuta la modificacion del usuario.  //
     // Y se activa cuando se toca el boton de modificacion de usuario. //
    /////////////////////////////////////////////////////////////////////
    private TextView mResponseTv; // Aviso de mensaje
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";
    // Url principal
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final EditText name = (EditText) findViewById(R.id.et_name);
        final EditText last_name = (EditText) findViewById(R.id.et_last_name);
        final EditText password = (EditText) findViewById(R.id.et_password);

        /*
        * Los EditText que username, name y last_name son importantes
         */

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnActionUpdate = (Button) findViewById(R.id.btn_update);

        /*
        * Se inicializa mResponseTv pero no se puede ver
        * btnActionUpdate se inicializa, este boton sera el ejectutor del envio
         */

        btnActionUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = username.getText().toString();           ///////////////////////////////////
                Patch patch = new Patch(name.getText().toString(),    // Se inicializa el nick String  //
                        last_name.getText().toString());             // Se pasan los datos a patch    //
                sendUpdate(nick, patch);                            // Y se llama al metodo de modi- //
                                                                   //        el usuario             //
                                                                  ///////////////////////////////////
            }
        });
    }

    private void sendUpdate(String username, Patch patch) {
        Retrofit retrofit =                                                    //////////////////////////
                new Retrofit.Builder()                                        //  Aqui se manda el    //
                        .baseUrl(BASE_URL)                                   //  URL verifica y      //
                        .addConverterFactory(GsonConverterFactory.create()) // se convierte en Json //
                        .build();                                          // cada dato que este   //
        UserClient client = retrofit.create(UserClient.class);            //     dentro de el.    //
                                                                         //////////////////////////
        Call<Patch> call = client.sendUpdate(username, patch);
        call.enqueue(new Callback<Patch>() {
            @Override
            public void onResponse(Call<Patch> call, Response<Patch> response) {
                showResponse("User Updated Successfull");                ////////////////////////////////////////////
            }                                                           // Si la respuesta es satisfactoria       //
                                                                       // envia el mensaje de usuario modificado,//
            @Override                                                 // sino, no te enviara nada               //
            public void onFailure(Call<Patch> call, Throwable t) {   // Si la respuesta no se efectuo          //
                showResponse("Oh no! Error of server");             // Te envia un error del server           //
            }                                                      ////////////////////////////////////////////
        });
    }

    public void showResponse(String response) {
                                                        ///////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mResponseTv.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                            ///////////////////////////////////////////
        mResponseTv.setText(response);
    }
}