package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.Tareas;
import com.exam.sid.aplicacion.remote.ApiUtils;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elias Barrientos on 12/5/2017.
 */

public class Task extends AppCompatActivity {
         ////////////////////////////////////////////////////////////////////////
        // Esta es la clase que muestra las tareas y botones de ejecucion.    //
       // Ventana de inicio donde se pueden visualizar las diferentes tareas,//
      // y actividades que se deben realizar, los botones de ejecucion se   //
     // utilizan para manejar la creacion, modificacion y eliminacion      //
    //                       de las tareas.                               //
   ////////////////////////////////////////////////////////////////////////

    TextView mResponseTv, mWelcome; // Los avisos de mensaje
    UserClient mAPIService;
    Button btnTask, btnDelete, btnUpdate; //Botones de accion, aqui se ejecutan los procesos
    Tareas[] tareas; // Arreglo de las tareas que se lean en la base de datos

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        Bundle datos = this.getIntent().getExtras();
        String token = datos.getString("variable_string");
        String name = datos.getString("variable_name");

        /*
        * Se obtienen los datos suministrados por la clase Main
        */

        mAPIService = ApiUtils.getAPIService();
        mResponseTv = (TextView) findViewById(R.id.tv_response);
        mWelcome = (TextView) findViewById(R.id.welcome);
        verBienvenida(name);
        final String nick = datos.getString("variable_username");
        final TextView descripcion = (TextView) findViewById(R.id.et_descripcion);
        final TextView categoria = (TextView) findViewById(R.id.et_categoria);
        final TextView numero = (TextView) findViewById(R.id.numeritos);
        final TextView updDescripcion = (TextView) findViewById(R.id.cambio_descripcion);
        final TextView updCategoria = (TextView) findViewById(R.id.cambio_categoria);
        final TextView updNumero = (TextView) findViewById(R.id.updnumeritos);
        Button SelectGet = (Button) findViewById(R.id.Ver_tarea);
        Button SelectTask = (Button) findViewById(R.id.btn_tarea);
        Button SelectDelete = (Button) findViewById(R.id.eliminar_tarea);
        Button SelectUpdate = (Button) findViewById(R.id.modificar_tarea);
        btnTask = (Button) findViewById(R.id.guardar_tarea);
        btnDelete = (Button) findViewById(R.id.deletetask);
        btnUpdate = (Button) findViewById(R.id.updatetask);

        /*
        * Se inicializan los botones de mResponseTv y mWelcome sin ser visibles
        * Se pasan los datos de los Textview ubicados en el layout task
        * Se asignan los botones de las tareas Crear, Ver y Eliminar
        */

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorWelcome(0xab000000);
                showWelcome("Cargando...");                                 //////////////////////////////////////////////
                Tareas tareas = new Tareas(categoria.getText().toString(), // Si se da click en el boton de accion     //
                        descripcion.getText().toString());                // btnTask se guardaran los datos de tareas.//
                MostrarEnvio(descripcion, categoria);                    // Se haran invisibles los Textview y       //
                sendTask(nick, tareas);                                 // el boton. Se llama al metodo sendTask.   //
                                                                       //////////////////////////////////////////////
            }
        });

        SelectTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                        //////////////////////////////////////////////////
                                                       // Al tocar SelectTask se muestran los TextView //
                MostrarEnvio(descripcion, categoria); // y el boton que se usaran para poder crear la //
                                                     //      tarea del usuario registrado.           //
                                                    //////////////////////////////////////////////////
            }
        });

        SelectGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorResponse(0xab000000);              ///////////////////////////////////////////////
                colorWelcome(0xab000000);              // Al tocar SelectGet se muestra un aviso de //
                showWelcome("Cargando...");           // mensaje de "Cargando..." y me envia al    //
                getTask(nick);                       //      metodo de leer tareas                //
                                                    ///////////////////////////////////////////////
            }
        });

        SelectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                              //////////////////////////////////////////////////
                                             // Al tocar SelectDelete se muestra un TextView //
                MostrarDelete(numero);      // del numero de la tarea y el boton btnDelete. //
                                           //////////////////////////////////////////////////

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                                                    /////////////////////////////////////
                colorWelcome(0xab000000);                                          // Al tocar btnDelete se pasa como //
                showWelcome("Cargando...");                                       // parametro el numero de la tarea //
                DeleteTask(Integer.parseInt(numero.getText().toString())-1,nick);// y me envia al metodo DeleteTask.// 
                                                                                ///////////////////////////////////// 
                                                                              

            }
        });

        SelectUpdate.setOnClickListener(new View.OnClickListener() {
            @Override                                                         /////////////////////////////////////
            public void onClick(View view) {                                 // Si toco este boton se muestran  //
                MostrarUpdate(updDescripcion, updCategoria, updNumero);     // los Textview y el boton de      //
            }                                                              // modificacion de tarea           //
                                                                          /////////////////////////////////////
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorWelcome(0xab000000);
                showWelcome("Cargando...");
                Tareas tareas = new Tareas(updCategoria.getText().toString(),     /////////////////////////////
                                          updDescripcion.getText().toString());  // Si toco el boton se     //
                updateTask(nick,tareas,                                         // pasan los datos que se  //
                        Integer.parseInt(updNumero.getText().toString())-1);   // cambiaran con dos iden- //
            }                                                                 // tificadores.            //
                                                                             /////////////////////////////
        });
    }

    public void verBienvenida(String name){
        showWelcome("Bienvenido "+name); //Se envia el mensaje de bienvenida al usuario
    }

    public void verToken(String token){
        showResponse(token); //Se muestra el token (Solo era para comprobar que se obtenia
    }

    public void showResponse(String response) {
                                                        ///////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mResponseTv.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                            ///////////////////////////////////////////
        mResponseTv.setText(response);
    }

    public void colorResponse(int color){
        mResponseTv.setBackgroundColor(color);
    }

    public void colorWelcome(int color){
        mWelcome.setBackgroundColor(color);
    }

    public void showWelcome(String response) {
        mWelcome.setText(response); //Luego de dar la bienvenida se puede modificar
    }

    public void MostrarEnvio(TextView descripcion, TextView categoria){
        if(btnTask.getVisibility() == View.GONE){
            descripcion.setVisibility(View.VISIBLE);      /////////////////////////////////
            categoria.setVisibility(View.VISIBLE);       //  Aqui es donde se muestran  //
            btnTask.setVisibility(View.VISIBLE);        // Los textView de descripcion //
        }                                              // y categoria. Si estan invi- //
        else{                                         // sibles se hacen visibles.   //
            descripcion.setVisibility(View.GONE);    // Si estan visibles se hacen  //
            categoria.setVisibility(View.GONE);     //        invisibles.          //
            btnTask.setVisibility(View.GONE);      /////////////////////////////////
        }
    }

    public void MostrarDelete(TextView numero){
        if(btnDelete.getVisibility() == View.GONE){
            btnDelete.setVisibility(View.VISIBLE);       ///////////////////////////////////////////////
            numero.setVisibility(View.VISIBLE);         // Aqui se muestra el TextView de numero     //
        }                                              // y el boton de Eliminar. Si estan visibles //
        else{                                         // se hacen invisibles. Si estan invisibles  //
            btnDelete.setVisibility(View.GONE);      //          se hacen visibles                //
            numero.setVisibility(View.GONE);        ///////////////////////////////////////////////
        }
    }

    public void MostrarUpdate(TextView descripcion,
                              TextView categoria, TextView numero){
        if(btnUpdate.getVisibility() == View.GONE){
            btnUpdate.setVisibility(View.VISIBLE);      //////////////////////////////////////////////////
            descripcion.setVisibility(View.VISIBLE);   // Aqui se muestran los TextView de descripcion,//
            categoria.setVisibility(View.VISIBLE);    // categoria y el boton de Cambiar.             //
            numero.setVisibility(View.VISIBLE);      // Si estan visibles se hacen invisibles.       //
        }                                           // Si estan invisibles se hacen visibles        //
        else {                                     //////////////////////////////////////////////////
            btnUpdate.setVisibility(View.GONE);
            descripcion.setVisibility(View.GONE);
            categoria.setVisibility(View.GONE);
            numero.setVisibility(View.GONE);
        }
    }

    private void sendTask(final String username, Tareas tareas){
        mAPIService.sendTask(username, tareas).enqueue(new Callback<Tareas>(){
            @Override
            public void onResponse(Call<Tareas> call, Response<Tareas> response) {  ////////////////////////////////////////
                if(response.isSuccessful()){                                       // Si la respuesta es satisfactoria,  //
                    colorWelcome(0xd810700e);                                     // Entonces se envia un aviso de      //
                    showWelcome("Tarea guardada");                               // Sino no se envia nada. Si la res-  // 
                }                                                               // puesta no llego,    se dice que    //
                else{                                                          // Hay un problema con la conexion    //
                    colorWelcome(0xeadc4126);                                 ////////////////////////////////////////
                    showWelcome("Tarea no guardada");
                }// de que la tarea se mando con exito //
            }                                                                  
                                                                              
            @Override                                                         
            public void onFailure(Call<Tareas> call, Throwable t) {          
                    showWelcome("No se guardo... Problema con la conexion");
            }
        });
    }

    private void getTask(String username){
        mAPIService.getTask(username).enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()){
                    tareas = response.body().getTareas();

                    if(tareas.length != 0){
                        String res = "";                                      ////////////////////////////////////////
                        for(int i = 0; i<tareas.length; i++){                // Se leen las tareas que estan       //
                            res = res+(i+1)+") "+tareas[i].getDescripcion()+// Si la respuesta es satisfactoria   //
                                                               "\n\n";     // Se verifica que hayan tareas       //
                        }                                                 // Si hay tareas se usa un arreglo    //
                        showResponse(res);                               // y una variable String para mostrar //
                        showWelcome("");                                // la descripcion y el id             //
                    }                                                  ////////////////////////////////////////
                    else{
                        showWelcome("Lista de tareas vacia");
                    }
                }
            }
            @Override
            public void onFailure(Call<Get> call, Throwable t) {          /////////////////////////////////////////
                     showWelcome("Hay un problema con el servidor");     // Si la respuesta no se pudo ejecutar //
            }                                                           /////////////////////////////////////////
        });
    }

    public void DeleteTask(int numero, String username){
        String _id = "";
        for(int i=0; i<tareas.length; i++){      ///////////////////////////////////////////////////////
            if(i == numero){                    // Para activar este metodo es necesario ver         //
                _id = tareas[i].get_id();      // las tareas para saber cual se eliminara           //
            }                                 // Ya que se observa cada elemento de tareas         //
        }                                    // Para poder obtener su id y pasarlo a la interface //
                                            ///////////////////////////////////////////////////////
        mAPIService.deleteTask(username, _id).enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()){
                    colorResponse(0xd810700e);
                    showResponse("Tarea Eliminada");            ////////////////////////////////////////////
                }                                              // Si la respuesta es satisfactoria       //
                else{  colorResponse(0xeadc4126);             // Se envia el mensaje de tarea eliminada //
                    showResponse("Tarea no Eliminada");}     // Sino se envia lo contrario             //
            showWelcome("");                                ////////////////////////////////////////////
            }                                               
            @Override
            public void onFailure(Call<Get> call, Throwable t) {   /////////////////////////////////////
                showResponse("Error");                            // Si no se ejecuta las respuestas //
            }                                                    /////////////////////////////////////
        });
    }

    public void updateTask(final String username, Tareas task, int numero){
        String _id = "";
        for(int i=0; i<tareas.length; i++){      ///////////////////////////////////////////////////////
            if(i == numero){                    // Para activar este metodo es necesario ver         //
                _id = tareas[i].get_id();      // las tareas para saber cual se eliminara           //
            }                                 // Ya que se observa cada elemento de tareas         //
        }                                    // Para poder obtener su id y pasarlo a la interface //
                                            ///////////////////////////////////////////////////////
        mAPIService.updateTask(username, _id, task).enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        colorResponse(0xd810700e);
                        showResponse("Una tarea ha sido modificada");  /////////////////////////////////////
                    } else {  colorResponse(0xeadc4126);              // Si la tarea se modifica         //
                        showWelcome("La tarea esta vacia");          // satisfactoriamente entonces     //
                    }                                               // se manda un aviso de mensaje    //
                }                                                  // de tarea cambiada, sino se      //
                else{  colorResponse(0xeadc4126);                 // manda error, y si no se envia   //
                       showWelcome("Ha ocurrido un error");      // la peticion se manda error del  //
                }                                               //           servidor              //
                showWelcome("");                               /////////////////////////////////////
            }                                                  
            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                      showWelcome("Error con el servidor");
            }
        });
    }
}
