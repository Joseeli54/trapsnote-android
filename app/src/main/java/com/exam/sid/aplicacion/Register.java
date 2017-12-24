package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.model.Post;
import com.exam.sid.aplicacion.service.UserClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
        ////////////////////////////////////////////////////////////////////////
       // Esta es la clase para registrar usuario, donde se pasan los datos. //
      // Maneja el layout register.xml. Una vez registrado el usuario se    //
     // puede retroceder a la vista de inicion de sesion.                  //
    // /////////////////////////////////////////////////////////////////////

    private TextView mResponseTv; //Aviso de mensaje
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";
    //URL principal del restApp

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final EditText name = (EditText) findViewById(R.id.et_name);
        final EditText last_name = (EditText) findViewById(R.id.et_last_name);
        final EditText email = (EditText) findViewById(R.id.et_email);
        final EditText password = (EditText) findViewById(R.id.et_password);

        final EditText dia = (EditText) findViewById(R.id.dia);
        final EditText mes = (EditText) findViewById(R.id.mes);
        final EditText year = (EditText) findViewById(R.id.year);

        /*
        * Se inicializan las variables de cada EditText requerido para el registro
        */

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnActionRegister = (Button) findViewById(R.id.guardar_registro);

        /*
        * mResponseTv nuevamente se inicializa pero se queda invisible
        * btnActionRegister se encarga de generar el envio de usuario
        */

        btnActionRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fecha = year.getText().toString()+"-"+
                        mes.getText().toString()+"-"+
                        dia.getText().toString();

                SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
                                                       ///////////////////////////////////
                Date fechaProg = null;                //       Si toco el boton        //
                                                     //     de btnActionRegister      //
                try {                               //     Los String de fecha se    //
                    fechaProg = sdfg.parse(fecha); // convierten en date, se pasan  //
                } catch (ParseException e) {      // los datos del usuario al Post //
                    e.printStackTrace();         // y se llama al metodo de envio //
                }                               ///////////////////////////////////

                Post post = new Post(username.getText().toString(),
                        name.getText().toString(),last_name.getText().toString(),
                        email.getText().toString(), password.getText().toString(),
                        fechaProg);

                sendNetworkRequest(post);
            }
        });
    }

    private void sendNetworkRequest(Post post) {
        Retrofit retrofit =                                                    //////////////////////////
                new Retrofit.Builder()                                        //  Aqui se manda el    //
                        .baseUrl(BASE_URL)                                   //  URL verifica y      //
                        .addConverterFactory(GsonConverterFactory.create()) // se convierte en Json //
                        .build();                                          // cada dato que este   //
        UserClient client = retrofit.create(UserClient.class);            //     dentro de el.    //
                                                                         //////////////////////////
        Call<Post> call = client.createAccount(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {                        /////////////////////////////////////////////////
                    showResponse("User Create Sucessfull");         // Se crea la llamada al metodo CreateAccount. //
                }                                                  // Si la respuesta es satisfactoria,se creo el //
                else{                                             //  usuario, pero sino se manda un aviso de    //
                    showResponse("We can't identify error");     //                 error                       //
                }                                               /////////////////////////////////////////////////
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) { //////////////////////////////////////
                showResponse("Error Service");                   // Si no se puede hacer la peticion //
            }                                                   //////////////////////////////////////
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

