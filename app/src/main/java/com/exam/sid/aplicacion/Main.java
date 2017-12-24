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
        //////////////////////////////////////////////////////////////////////
       // Esta es la clase principal, donde se realiza el Inicio de Sesion.//
      // Maneja el layout login.xml, y tambien contiene los botones que   //
     // conduce a la vista de tareas y registro.                         //
    // ///////////////////////////////////////////////////////////////////

    private Usuarios usuario; //Aqui se guarda los datos del usuario que inicia sesion
    private TextView mResponseTv; //Mensaje de aviso
    private Button btn_Update; //Boton de modificacion de datos de usuario
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com";
    //URL principal del restApp

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final EditText email = (EditText) findViewById(R.id.login_email);
        final EditText password = (EditText) findViewById(R.id.login_password);

        /*
        * El username solo se tiene para pasarse a la vista de tareas
        * El email se requiere para el inicio de sesion
        * El password se requiere para el inicio de sesion
        */

        btn_Update = (Button) findViewById(R.id.to_update);
        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnRegister = (Button) findViewById(R.id.register_button);
        Button btnGetList = (Button) findViewById(R.id.search_button);
        Button btnSignIn = (Button) findViewById(R.id.signin_button);

        /*
        * Asignacion de los botones que esta en el layout login
        * btnRegister, btnSignIn necesarios para el registro e inicio de sesion
        * mResponseTv se inicializa pero todavia no se puede ver
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

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                                                                  ///////////////////////////////////////////////////
                showResponse("Cargando...");                     //       Si se tocase el boton de Sign In,       //
                Post post = new Post(email.getText().toString(),// aparece el aviso "Cargando..", se insertan    //
                        password.getText().toString());        //los datos de email y password, y se implementa //
                sendLogin(post, email, password);             //     el metodo que envia los datos.            //
                                                             ///////////////////////////////////////////////////
            }
        });

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                              ////////////////////////////////////////
                String nick = username.getText().toString(); // Se pasa el username a una variable //
                getList(nick);                              // de tipo String y se leen un solo   //
                                                           //              usuario.              //
                                                          ////////////////////////////////////////
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                                              //////////////////////////////////
                                             //    Si se presiona el boton   //
                mover_a_Registro();         // Register se cambia al layout //
                                           //          de registro         //
                                          //////////////////////////////////
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                                                 //////////////////////////////////
                                                //    Si se presiona el boton   //
                mover_a_Actualizacion();       // Register se cambia al layout //
                                              //          de registro         //
                                             //////////////////////////////////
            }
        });

    }

    private void sendLogin(Post post, final TextView email, final TextView password){

        Retrofit retrofit =                                                    //////////////////////////
                new Retrofit.Builder()                                        //  Aqui se manda el    //
                        .baseUrl(BASE_URL)                                   //  URL verifica y      //
                        .addConverterFactory(GsonConverterFactory.create()) // se convierte en Json //
                        .build();                                          // cada dato que este   //
        UserClient client = retrofit.create(UserClient.class);            //     dentro de el.    //
                                                                         //////////////////////////
        Call<Post> listCall = client.postLogin(post);
        listCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                                                                                   /////////////////////////////////////////////
                if(response.code() == 200){                                       // Se crea la llamada al metodo postLogin. //
                    String token = response.headers().get("x-auth");             // Para ello se utiliza enqueue y Callback,//
                                                                                // que te devuelven las respuesta de la    //
                    if(token != null){                                         //  peticion. Si code == 200 y el token    //
                        tocoResponse();                                       //     obtenido del header no es nulo,     //
                        mover_a_Tareas(token, response.body().getName()+" "+ //   se pasan los datos obtenidos a Task.  //
                                        response.body().getLastName(),      //    Si code == 400 el usuario no existe  //
                                        response.body().getUsername());    //       O algun dato esta mal escrito.    //
                        DeleteDate(email,password);                       //       Al finalizar se borran los datos  //
                    }                                                    //             que se escribieron.         //
                }else if(response.code() == 400){                       /////////////////////////////////////////////
                    showResponse("Correo/Contraseña incorrecto");
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {     /////////////////////////////////////////////
                showResponse("Problem Connection");                  // Cuando no se puede realizar la peticion //
            }                                                       /////////////////////////////////////////////
        });


    }

    private void getList(String username) {

        Retrofit retrofit =                                                    //////////////////////////
                new Retrofit.Builder()                                        //  Aqui se manda el    //
                        .baseUrl(BASE_URL)                                   //  URL verifica y      //
                        .addConverterFactory(GsonConverterFactory.create()) // se convierte en Json //
                        .build();                                          // cada dato que este   //
                                                                          //     dentro de el.    //
        UserClient client = retrofit.create(UserClient.class);           //////////////////////////

        Call<Get> listCall = client.getList(username);
                                                                               /////////////////////////////////////////////
        listCall.enqueue(new Callback<Get>() {                                // Se crea la llamada al metodo getList.   //
            @Override                                                        //     Para ello se utiliza enqueue y      //
            public void onResponse(Call<Get> call, Response<Get> response) {//    Callback, que te devuelven las       //
                if (response.isSuccessful()) {                             //      respuestas de la peticion.         //
                    usuario = response.body().getUsuario();               //  Si fue satisfactorio, se muestran los  //
                    if (usuario != null) {                               //  los datos obtenidos y se hace visible  //
                        showResponse(usuario.getName()+" "              //              el boton Update.           //
                                +usuario.getLast_name());              //    Si code == 400 el usuario no existe  //
                        showUpdate();                                 //       O algun dato esta mal escrito.    //
                    }                                                /////////////////////////////////////////////
                }
            }

            @Override
            public void onFailure(Call<Get> call, Throwable t) {            /////////////////////////////////////////////
                Log.e(getApplicationContext().toString(), t.getMessage()); // Cuando no se puede realizar la peticion //
            }                                                             /////////////////////////////////////////////
        });

    }

    public void tocoResponse(){
                                                              ////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.VISIBLE) {    // Metodo que hace invisible el aviso //
            mResponseTv.setVisibility(View.GONE);           //              de mensaje.           //
        }                                                  ////////////////////////////////////////
    }

    public void showResponse(String response) {
                                                        ///////////////////////////////////////////
        if(mResponseTv.getVisibility() == View.GONE) { // Aqui hago visible el aviso de mensaje //
            mResponseTv.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                            ///////////////////////////////////////////
        mResponseTv.setText(response);
    }

    public void showUpdate() { 
                                                       ///////////////////////////////////////////
        if(btn_Update.getVisibility() == View.GONE) { // Aqui hago visible el boton de Update  //
            btn_Update.setVisibility(View.VISIBLE);  //     Y le agrego un nuevo texto        //
        }                                           ///////////////////////////////////////////
    }

    public void DeleteDate(TextView email, TextView password){
                                     ////////////////////////////
        email.setText("");          // Borro dato de email    //
        password.setText("");      // Borro dato de password //
                                  ////////////////////////////
    }

    public void mover_a_Registro() {
                                                                                 /////////////////////////////////
        Intent ListSong = new Intent(getApplicationContext(), Register.class);  // Paso a la vista de registro //
        startActivity(ListSong);                                               //    No paso ningun parametro //
                                                                              /////////////////////////////////
    }

    public void mover_a_Tareas(String token, String name, String username) {
        Intent ListSong = new Intent(getApplicationContext(), Task.class); ////////////////////////////////
        ListSong.putExtra("variable_string", token);                      //  Paso a la vista de Task   //
        ListSong.putExtra("variable_name", name);                        //  Paso el token, el nombre  //
        ListSong.putExtra("variable_username", username);               // del usuario y su username  //
        startActivity(ListSong);                                       ////////////////////////////////
    }

    public void mover_a_Actualizacion() {
                                                                              /////////////////////////////////
        Intent ListSong = new Intent(getApplicationContext(), Update.class); // Paso a la vista de update   //
        startActivity(ListSong);                                            //   No paso ningun parametro  //
                                                                           /////////////////////////////////
    }

}
