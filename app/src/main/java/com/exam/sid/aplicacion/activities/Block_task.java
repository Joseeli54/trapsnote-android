package com.exam.sid.aplicacion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exam.sid.aplicacion.R;
import com.exam.sid.aplicacion.model.ErrorPojoClass;
import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Tareas;
import com.exam.sid.aplicacion.remote.ApiUtils;
import com.exam.sid.aplicacion.service.UserClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elias Barrientos on 1/1/2018.
 */

public class Block_task extends AppCompatActivity {

    TextView mResponseTv, mWelcome; // Los avisos de mensaje
    UserClient mAPIService; // Usuario cliente
    Tareas[] tareas; // Arreglo de las tareas que se lean en la base de datos
    private String token, name, username; // Strind del token, username y name
    private String description,category,id, nombretask; // String de los datos de las tareas
    private String[] descripcion = new String[1000];  
    private String[] categoria = new String[1000];    ////////////////////////////////////////
    private String[] iden = new String[1000];        // Arreglos de los datos de las tareas//
    private String[] nametask = new String[1000];   ////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_task);

        Bundle datos = this.getIntent().getExtras();

        final int peticion = datos.getInt("peticion");        ////////////////////////////////////////
        token = datos.getString("variable_string");          // Adquiero los datos suministrados   //
        name = datos.getString("name");                     //          por la clase Task         //
        username = datos.getString("username");            ////////////////////////////////////////
        nombretask = datos.getString("nombre");
        description = datos.getString("descripcion");
        category = datos.getString("categoria");
        id = datos.getString("id");

        final TextView descripcion = (TextView) findViewById(R.id.et_descripcion);
        final TextView categoria = (TextView) findViewById(R.id.et_categoria);
        final TextView nombre = (TextView) findViewById(R.id.et_nombre);

        /*
        * Inicializo los textview existentes en la ventana de Block_task
        */

        nombre.setText(nombretask);
        descripcion.setText(description);
        categoria.setText(category);

        /*
        * Aqui les agrego los datos que se pasaron de la clase task, si no vinieron vacios 
        * Tendremos los datos de la tareas existente que se ha seleccionado en Task
        */

        Button delete = (Button) findViewById(R.id.deletetask);
        Button save = (Button) findViewById(R.id.guardar_tarea);
        mAPIService = ApiUtils.getAPIService();
        mWelcome = (TextView) findViewById(R.id.welcome);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        /*
        * Los botones de save y delete son los unicos que se utilizaran en esta clase
        * Y el delete se activa cuando se selecciona una tarea en la clase task
        */

        if(peticion == 1){ // Si se selecciono una de las tareas de task ya existente, se activa delete
            delete.setVisibility(View.VISIBLE);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorWelcome(0xab000000);
                showWelcome("Cargando...");                                 //////////////////////////////
                Tareas tareas = new Tareas(categoria.getText().toString(), // Se pasan los datos a una //
                        descripcion.getText().toString(),                 // variables de Tareas para //
                        nombre.getText().toString());                    //      Guardarlas ahi.     //
                                                                        //////////////////////////////
                if(peticion == 0) // Si solo se toco el boton de crear tarea, se crea la tarea
                sendTask(username, tareas);
                else if (peticion == 1) // Si se presiono una tarea, se modifica la tarea
                    updateTask(username, tareas, id);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    ////////////////////////////////////////
                colorWelcome(0xab000000);      // Si se toco el boton de delete      //
                showWelcome("Cargando...");   // inmediatamente se elimina la tareas//
                DeleteTask(id, username);    ////////////////////////////////////////
            }
        });
    }

    private void sendTask(final String username, Tareas tareas){
        mAPIService.sendTask(username, tareas).enqueue(new Callback<Tareas>(){
            @Override
            public void onResponse(Call<Tareas> call, Response<Tareas> response) {  ////////////////////////////////////////
                if(response.isSuccessful()){                                       // Si la respuesta es satisfactoria,  //
                                                                                  // Entonces se envia el username      //
                                                                                 // a getTask, para volver a cargar las//
                    getTask(username);                                          // Tareas que esten existentes en ese //
                }                                                              // momento y pasarlas a la clase Task //  
                else{                                                         ////////////////////////////////////////  
                    Gson gson = new GsonBuilder().create();
                    ErrorPojoClass mError= new ErrorPojoClass();
                    //La variable gson se crea para poder ingresar ahi el JSON de respuesta
                    // Se instancia la variable de la clase ErrorPojoClass
                    try {
                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass.class);
                        // Se leen los datos y dependiendo de las variables que esten ErrorPojoClass
                        // Se pasan los datos a las variables de esa clase
                        colorWelcome(0xeadc4126);
                        showWelcome(mError.getMessage());

                    } catch (IOException e) {
                        // handle failure to read error
                    }
                }// de que la tarea se mando con exito //
            }

            @Override
            public void onFailure(Call<Tareas> call, Throwable t) {
                showWelcome("No se guardo... Problema con la conexion"); // Aqui hay un problema de conectividad
            }
        });
    }

    private void updateTask(final String username, Tareas task, String _id){
        mAPIService.updateTask(username, _id, task).enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {       ////////////////////////////////////////
                if(response.isSuccessful()) {                                     // Si la respuesta es satisfactoria,  //
                    getTask(username);                                           // Entonces se envia el username      //
                }                                                               // a getTask, para volver a cargar las//
                else{                                                          // Tareas que esten existentes en ese //
                    Gson gson = new GsonBuilder().create();                   // momento y pasarlas a la clase Task //  
                    ErrorPojoClass mError= new ErrorPojoClass();             //////////////////////////////////////// 
                    //La variable gson se crea para poder ingresar ahi el JSON de respuesta
                    // Se instancia la variable de la clase ErrorPojoClass
                    try {
                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass.class);
                        // Se leen los datos y dependiendo de las variables que esten ErrorPojoClass
                        // Se pasan los datos a las variables de esa clase
                        colorWelcome(0xeadc4126);
                        showWelcome(mError.getMessage());

                    } catch (IOException e) {
                        // handle failure to read error
                    }
                }
            }
            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                showWelcome("Hay un problema con el servidor");  // Aqui hay un problema de conectividad
            }
        });
    }

    private void DeleteTask(String _id, final String username){
        mAPIService.deleteTask(username, _id).enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()){
                    getTask(username);                          //////////////////////////////////////////////
                }                                              // Si la respuesta es satisfactoria pasa la //
                                                              // lo mismo que con las otras peticiones    //
            }                                                //////////////////////////////////////////////
            @Override
            public void onFailure(Call<Get> call, Throwable t) {   //////////////////////////////////////
                showResponse("Hay un problema con el servidor");  // Si no se ejecutan las respuestas //
            }                                                    //////////////////////////////////////
        });
    }

    private void getTask(final String username){
        mAPIService.getTask(username).enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()){                                 //////////////////////////////////////////
                    tareas = response.body().getTareas();                   // Aqui se leen las tareas y si existen // 
                    if(tareas.length != 0){                                // tareas en la base de datos del usua- // 
                        for(int i=0; i<tareas.length; i++){               // rio se pasan a los arreglos corres-  //
                            categoria[i] = tareas[i].getCategoria();     // pondientes a pasar a Task, sino hay  //
                            descripcion[i] = tareas[i].getDescripcion();// tareas, no se agregan datos y los    //
                            iden[i]= tareas[i].get_id();               // arreglos quedan nulos.               //
                            nametask[i] = tareas[i].getNombre();      //////////////////////////////////////////
                        }
                    }
                    tocoWelcome();
                    mover_a_Tareas(username);
                }
            }
            @Override
            public void onFailure(Call<Get> call, Throwable t) {          /////////////////////////////////////////
                showWelcome("Hay un problema con el servidor");          // Si la respuesta no se pudo ejecutar //
            }                                                           /////////////////////////////////////////
        });
    }

    public void MostrarEnvio(TextView descripcion, TextView categoria, TextView nombre){
                                                  /////////////////////////////////
        descripcion.setVisibility(View.GONE);    // Si estan visibles se hacen  //
        categoria.setVisibility(View.GONE);     //        invisibles.          //
        nombre.setVisibility(View.GONE);       /////////////////////////////////

    }


    public void showResponse(String response) {
                                                        ///////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mResponseTv.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                            ///////////////////////////////////////////
        mResponseTv.setText(response);
    }


    public void colorWelcome(int color){
        mWelcome.setBackgroundColor(color); //El aviso de mensaje se puede colorear
    }

    public void showWelcome(String response) {
        mWelcome.setText(response); //El aviso de mensaje se puede modificar
    }

    public void tocoWelcome(){
        mWelcome.setVisibility(View.GONE); // Se quita el aviso de la pantalla
    }

    public void mover_a_Tareas(String username) {
        Intent ListSong = new Intent(getApplicationContext(), Task.class); ////////////////////////////////
        ListSong.putExtra("variable_string", token);                      //  Paso a la vista de Task   //
        ListSong.putExtra("name", name);                                 //  Paso el token, el nombre  //
        ListSong.putExtra("username", username);                        // y los otros datos propios  //
        ListSong.putExtra("nombre", nametask);                         //  del usuario y sus tareas  //
        ListSong.putExtra("descripcion", descripcion);                ////////////////////////////////      
        ListSong.putExtra("categoria", categoria);                        
        ListSong.putExtra("id", iden);
        ListSong.putExtra("tamano", tareas.length);
        startActivity(ListSong);                                       
        finish();
    }

    @Override
    public void onBackPressed() {
        colorWelcome(0xab000000);
        showWelcome("Cargando Tareas..."); // Si hecho para atras, se cargan las tareas y se llama al metodo
        getTask(username);                // getTask para regresarme a task
    }

}