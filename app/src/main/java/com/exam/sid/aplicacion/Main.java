package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.exam.sid.aplicacion.model.Post;
import com.exam.sid.aplicacion.remote.APIService;
import com.exam.sid.aplicacion.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private TextView mResponseTv;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*se crean variables finales de tipo EditText de los parametros que se utilizaran*/
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText lastName = (EditText) findViewById(R.id.SegundoNombre);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);

        Button submitBtn = (Button) findViewById(R.id.btn_submit); //Boton del layout llamado Submit
        mResponseTv = (TextView) findViewById(R.id.tv_response); //Aviso que te da los mensajes
        mAPIService = ApiUtils.getAPIService(); //aqui se instancia el mAPIService


        submitBtn.setOnClickListener(new View.OnClickListener() { //si presiono Submit
            @Override
            public void onClick(View view) {

                /*Se crean nuevas variables String y se les asigna las finales*/

                String nombre = name.getText().toString().trim();
                String last_nombre = lastName.getText().toString().trim();
                String correo = email.getText().toString().trim();
                String clave = password.getText().toString().trim();

                /*Se verifican las variables*/

                if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(correo) &&
                        !TextUtils.isEmpty(last_nombre) && !TextUtils.isEmpty(clave)) {

                    /*Se envian*/
                    sendPost(nombre, last_nombre, correo, clave);
                }
            }
        });

    }

    public void sendPost(String name, String last_name, String email, String password) {

        /*Aqui implemento el savePost de la clase ApiService*/
        mAPIService.savePost(name,last_name, email, password).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.isSuccessful()) { //Si es satisfactorio me envia el mensaje con todos los datos
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                } else { //Si no, me devuelve error
                    showResponse("Error");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) { //Si hay una excepcion o problema de red me envia esto
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) { //aqui hago visible el aviso de mensaje
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}