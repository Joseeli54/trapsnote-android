package com.exam.sid.aplicacion.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.exam.sid.aplicacion.R;
import com.exam.sid.aplicacion.model.ErrorPojoClass;
import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Tareas;
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
    private boolean[] completado = new boolean[1000];
    private String[] fechaLimit = new String[1000];
    private boolean listo; // Bolean de completado
    private Spinner spinner; // El Spinner que se usara para escoger las categorias
    EditText etPlannedDate; // Calendario de fecha limite
    private boolean Logica;

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
        listo = datos.getBoolean("completado");
        String fechaLimite = datos.getString("fechaLimite");

        final TextView descripcion = (TextView) findViewById(R.id.et_descripcion);
        final TextView categoria = (TextView) findViewById(R.id.et_categoria);
        final TextView nombre = (TextView) findViewById(R.id.et_nombre);
        final TextView completado = (TextView) findViewById(R.id.et_completado);
        final Validation validar = new Validation();
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] palabras = {"Estudios","Trabajo","Hogar","Actividad","Ejercicio","Plan","Informacion"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, palabras));
        etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        Logica = false;

        /*
        * Inicializamos spinner junto con un String[] llamado palabras
        * Inicializo los textview existentes en la ventana de Block_task
        */

        if(listo){ // Si listo es true se colorea completado de verde y se asigna completado
            completado.setBackgroundColor(0xD815A312);
            completado.setText("Completado");
        }
        else{ // Si listo es false se colorea completado de rojo y se asigna no completado
            completado.setBackgroundColor(0xD4AE880B);
            completado.setText("No Completado");
        }

        // Se escribe lo que se mando a los Textview
       
        nombre.setText(nombretask); 
        descripcion.setText(description);
        categoria.setText(category);

        if(peticion == 1) // Si ya existia una fecha limite esta se escribira en el etPlannedDate
        etPlannedDate.setHint(fechaLimite);

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

        mWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                    /////////////////////////////////////////////
                showWelcome("");                   //     Si se tocase el boton de aviso,     //
                tocoWelcome();                    // simplemente se vacia el texto y se pone //
                                                 //         invisible de nuevo.             //
                                                /////////////////////////////////////////////
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Logica == false) {
                    Logica = true;
                    Date fechaProg = convertir_Fecha(); // Te convertira los campos en Date

                    colorWelcome(0xab000000);
                    showWelcome("Cargando...");                                 //////////////////////////////
                    Tareas tareas = new Tareas(categoria.getText().toString(), // Se pasan los datos a una //
                            descripcion.getText().toString(),                 // variables de Tareas para //
                            nombre.getText().toString(), fechaProg);         //      Guardarlas ahi.     //
                    //////////////////////////////
                    if (peticion == 0) // Si solo se toco el boton de crear tarea, se crea la tarea
                        sendTask(username, tareas);
                    else if (peticion == 1) { // Si se presiono una tarea, se modifica la tarea
                        updateTask(username, tareas, id);
                    }

                    validar.campos_de_tareas(nombre, descripcion, categoria);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Logica == false) {
                    Logica = true;                  ////////////////////////////////////////
                    colorWelcome(0xab000000);      // Si se toco el boton de delete      //
                    showWelcome("Cargando...");   // inmediatamente se elimina la tareas//
                    DeleteTask(id, username);    ////////////////////////////////////////
                }
            }
        });

        completado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(peticion == 1){
                    if(listo){
                        //listo = false;
                        //completado.setBackgroundColor(Color.RED);
                        //completado.setText("No Completado");
                    }
                    else{

                        if(Logica == false) {
                            Logica = true;
                            colorWelcome(0xab000000);   // Si la peticion es 1 y completado es falso, puede pasarse
                            showWelcome("Cargando..."); // a completado true, pero no al reves

                            listo = true;
                            completado.setBackgroundColor(0xD815A312);
                            completado.setText("Completado");
                            Tareas tareas = new Tareas(listo);
                            updateCompletado(username, tareas, id);
                        }
                    }
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                if(peticion == 1){ // Si la categoria ya existe se pone la que ya se tenia
                    if(category != categoria.getText().toString() ||
                        (String)adapterView.getItemAtPosition(pos) != "Estudios")
                            categoria.setText((String) adapterView.getItemAtPosition(pos));
                }
                else // Si la categoria se pone por defecto la primera o la que se seleccione
                    categoria.setText((String) adapterView.getItemAtPosition(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });

        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(); //Este metodo se saco de internet
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
                        showWelcome(mError.getMessage()); // Aqui se envia un mensaje de error si pasa algo

                    } catch (IOException e) {
                        // handle failure to read error
                    }
                    Logica = false;
                }// de que la tarea se mando con exito //
            }

            @Override
            public void onFailure(Call<Tareas> call, Throwable t) {
                showWelcome("No se guardo... Problema con la conexion"); // Aqui hay un problema de conectividad
                Logica = false;
            }
        });
    }

    private void updateTask(final String username, Tareas task, String _id){
        mAPIService.updateTask(username, _id, task).enqueue(new Callback<Tareas>() {
            @Override
            public void onResponse(Call<Tareas> call, Response<Tareas> response) { ////////////////////////////////////////
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

                        if(mError.getMessage() != null)
                        showWelcome(mError.getMessage()); // Aqui se envia un mensaje de error si pasa algo
                        else
                        showWelcome(mError.getErrormsg());

                    } catch (IOException e) {
                        // handle failure to read error
                    }
                    Logica = false;
                }
            }
            @Override
            public void onFailure(Call<Tareas> call, Throwable t) {
                showWelcome("Hay un problema con el servidor");  // Aqui hay un problema de conectividad
                Logica = false;
            }
        });
    }

    private void updateCompletado(final String username, Tareas task, String _id){
        mAPIService.updateComplete(username, _id, task).enqueue(new Callback<Tareas>() {
            @Override
            public void onResponse(Call<Tareas> call, Response<Tareas> response) {
                if(response.isSuccessful())
                showWelcome("Se ha modificado"); // Si se modifica completado satisfactoriamente envia este aviso
                Logica = false;
            }

            @Override
            public void onFailure(Call<Tareas> call, Throwable t) {
                showWelcome("Hay un problema con el servidor"); // Si hay un problema con la conexion
                Logica = false;
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
                showWelcome("Hay un problema con el servidor");   // Si no se ejecutan las respuestas //
                Logica = false;                                  //////////////////////////////////////
            }
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
                            completado[i] = tareas[i].getCompletado();
                            if(tareas[i].getFechaLimite() != null)
                                fechaLimit[i] = tareas[i].getFechaLimite().toString();
                            else
                                fechaLimit[i] = "No hay fecha limite";
                        }
                    }
                    tocoWelcome();
                    mover_a_Tareas(username);
                }
                Logica = false;
            }
            @Override
            public void onFailure(Call<Get> call, Throwable t) {          /////////////////////////////////////////
                showWelcome("Hay un problema con el servidor");          // Si la respuesta no se pudo ejecutar //
                Logica = false;                                         /////////////////////////////////////////
            }
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
                                                     ///////////////////////////////////////////
        if(mWelcome.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mWelcome.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                         ///////////////////////////////////////////
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
        ListSong.putExtra("completado", completado);
        ListSong.putExtra("fechaLimite", fechaLimit);
        startActivity(ListSong);                                       
        finish();
    }

    @Override
    public void onBackPressed() {
        if(Logica == false) {
            Logica = true;
            colorWelcome(0xab000000);
            showWelcome("Cargando Tareas..."); // Si hecho para atras, se cargan las tareas y se llama al metodo
            getTask(username);                // getTask para regresarme a task
        }
    }

}