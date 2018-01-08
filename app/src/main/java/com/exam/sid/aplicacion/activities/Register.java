package com.exam.sid.aplicacion.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.R;
import com.exam.sid.aplicacion.model.ErrorPojoClass;
import com.exam.sid.aplicacion.model.Post;
import com.exam.sid.aplicacion.remote.ApiUtils;
import com.exam.sid.aplicacion.remote.DatePickerFragment;
import com.exam.sid.aplicacion.remote.Validation;
import com.exam.sid.aplicacion.service.UserClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
        ////////////////////////////////////////////////////////////////////////
       // Esta es la clase para registrar usuario, donde se pasan los datos. //
      // Maneja el layout register.xml. Una vez registrado el usuario se    //
     // puede retroceder a la vista de inicio de sesion.                   //
    ////////////////////////////////////////////////////////////////////////

    private UserClient mAPIService; // Aqui se asigna la variable del UserClient
    private Validation validar;    // Variable de validacion
    private TextView mResponseTv; //Aviso de mensaje
    EditText etPlannedDate; // Calendario que se modificara
    private boolean Logica;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final EditText name = (EditText) findViewById(R.id.et_name);
        final EditText last_name = (EditText) findViewById(R.id.et_last_name);
        final EditText email = (EditText) findViewById(R.id.et_email);
        final EditText password = (EditText) findViewById(R.id.et_password);

        /*
        * Se inicializan las variables de cada EditText requerido para el registro
        */

        validar = new Validation();
        mAPIService = ApiUtils.getAPIService();
        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnActionRegister = (Button) findViewById(R.id.guardar_registro);
        etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        Logica = false;

        /*
        * validar se utilizara para verificar los campos
        * mResponseTv nuevamente se inicializa pero se queda invisible
        * btnActionRegister se encarga de generar el envio de usuario
        */

        mResponseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                     /////////////////////////////////////////////
                showResponse("");                   //     Si se tocase el boton de aviso,     //
                tocoResponse();                    // simplemente se vacia el texto y se pone //
                                                  //         invisible de nuevo.             //
                                                 /////////////////////////////////////////////
            }
        });

        btnActionRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Logica == false) {
                    Logica = true;
                    Date fechaProg = convertir_Fecha(); // Te convertira los campos en Date

                    if (validar.Registerfull(username, name, last_name, email, password)) {  // Verifica si los campos existen
                        colorResponse(0xab000000);
                        showResponse("Cargando...");
                        Post post = new Post(username.getText().toString(),                   ////////////////////////
                                name.getText().toString(), last_name.getText().toString(),   // Se pasan los datos //
                                email.getText().toString(), password.getText().toString(),  // la variables post  //
                                fechaProg, "movil");                                       ////////////////////////

                        sendNetworkRequest(post); // Si son los correctos y las variables existen envia los datos
                    } else { // Sino existen los campos te manda un aviso para que los llenes
                        colorResponse(0xeadc4126);
                        showResponse("Todos los campos deben estar escritos");
                        Logica = false;
                    }
                                                                                           ////////////////////////////
                    validar.campos_de_textos(username, name, last_name, email, password); // Se colorean los campos //
                }                                                                        // que no esten escritos  //
            }                                                                           ////////////////////////////
        });

        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(); // Este metodo fue sacado de internet
            }
        });

    }

    private void showDatePickerDialog() {

         ////////////////////////////////////////////////////////////////
        // Aqui se selecciona la fecha que desea agregar el usuario   //
       ////////////////////////////////////////////////////////////////

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                etPlannedDate.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private Date convertir_Fecha(){
        String fecha = etPlannedDate.getText().toString();

        SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
                                               ///////////////////////////////////
        Date fechaProg = null;                //       Si toco el boton        //
                                             //     de btnActionRegister      //
        try {                               //     Los String de fecha se    //
            fechaProg = sdfg.parse(fecha); // convierten en date, se pasan  //
        } catch (ParseException e) {      // los datos del usuario al Post //
            e.printStackTrace();         // y se llama al metodo de envio //
        }                               ///////////////////////////////////

        return fechaProg;
    }

    private void sendNetworkRequest(Post post) {
        mAPIService.createAccount(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    colorResponse(0xd810700e);                         /////////////////////////////////////////////////
                    showResponse("Usuario creado Satisfactoriamente");// Se crea la llamada al metodo CreateAccount. //
                    Logica = false;                                  // Si la respuesta es satisfactoria,se creo el //
                }                                                   //  usuario, pero sino se manda un aviso de    //
                else{                                              //                 error                       //
                    Gson gson = new GsonBuilder().create();       /////////////////////////////////////////////////
                    ErrorPojoClass mError= new ErrorPojoClass();
                    //La variable gson se crea para poder ingresar ahi el JSON de respuesta
                    // Se instancia la variable de la clase ErrorPojoClass
                    try {
                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass.class);
                        // Se leen los datos y dependiendo de las variables que esten ErrorPojoClass
                        // Se pasan los datos a las variables de esa clase
                        colorResponse(0xeadc4126);
                        if(mError.getErrmsg()!=null) // Si el usuario es un duplicado
                            showResponse(mError.getErrmsg());
                        else{                       // Si hay otro error diferente
                            showResponse(mError.getMessage());
                        }
                    } catch (IOException e) {
                        // handle failure to read error
                    }

                    Logica = false;
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) { //////////////////////////////////////
                showResponse("Hay un problema con la conexion"); // Si no se puede hacer la peticion //
                Logica = false;                                 //////////////////////////////////////
            }
        });
    }

    public void tocoResponse(){
                                                              ////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.VISIBLE) {    // Metodo que hace invisible el aviso //
            mResponseTv.setVisibility(View.GONE);           //              de mensaje.           //
        }                                                  ////////////////////////////////////////
    }

    public void colorResponse(int color){         /////////////////////////////////
        mResponseTv.setBackgroundColor(color);   // Coloreo el mensaje de aviso //
    }                                           /////////////////////////////////

    public void showResponse(String response) {
                                                        ///////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mResponseTv.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                            ///////////////////////////////////////////
        mResponseTv.setText(response);
    }
}