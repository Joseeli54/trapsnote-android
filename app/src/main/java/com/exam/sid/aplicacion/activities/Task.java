package com.exam.sid.aplicacion.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.sid.aplicacion.R;
import com.exam.sid.aplicacion.remote.ApiUtils;
import com.exam.sid.aplicacion.service.UserClient;

/**
 * Created by Elias Barrientos on 12/5/2017.
 */

public class Task extends AppCompatActivity {
         ////////////////////////////////////////////////////////////////////////
        //                Esta es la clase que muestra las tareas.            //
       // Ventana de inicio donde se pueden visualizar los bloques de tareas,//
      // Aqui se pueden seleccionar cualquiera de las tareas para ser modi- //
     // ficadas y a las vez tambien poder eliminarlas, hay un boton arriba //
    //            que es donde se crean las nuevas tareas.                //
   ////////////////////////////////////////////////////////////////////////

          // El antiguo modelo se cambio para facilitar la utilizacion
         //            de esta aplicacion al usuario.

    private LinearLayout layout; // Se crea una variable layout
    private LinearLayout contenedorBoton[] = new LinearLayout[1000]; // Un contenedor es necesario para desplazar los
                                                            // bloques de tareas
    TextView btn[] = new TextView[1000]; // Y un contenedor de TextView tambien
    TextView btnOrigin; // Es necesario el textView actual en el cual 
                       // se duplicaran los otros y seran modificados
    private TextView mResponseTv, mWelcome; //Avisos de mensajes
    private UserClient mAPIService; // El cliente del servidor
    private int tamano; // El numero de tareas que se van a imprimir en pantalla
    private LinearLayout[] layoiut = new LinearLayout[1000];
    private String token, name, username;
    private String[] descripcion = new String[1000];
    private String[] categoria = new String[1000];    ////////////////////////////////////////
    private String[] id = new String[1000];          // Arreglos de los datos de las tareas//
    private String[] nombre = new String[1000];     ////////////////////////////////////////
    private boolean[] completado = new boolean[1000];
    private String[] fechaLimite = new String[1000];


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);

        Bundle datos = this.getIntent().getExtras();

        token = datos.getString("variable_string");              /////////////////////////////////////
        name = datos.getString("name");                         // Adquiero los datos que fueron   //
        username = datos.getString("username");                // Suministrados por la clase Main //
        tamano = datos.getInt("tamano");                      // para poder saber cuantas y que  //
        descripcion = datos.getStringArray("descripcion");   // tipo de tareas existen en la ba-//
        categoria = datos.getStringArray("categoria");      //     base de datos del usuario   //
        id = datos.getStringArray("id");                   /////////////////////////////////////
        nombre = datos.getStringArray("nombre");
        completado = datos.getBooleanArray("completado");
        fechaLimite = datos.getStringArray("fechaLimite");

        mAPIService = ApiUtils.getAPIService();
        mWelcome = (TextView) findViewById(R.id.welcome);
        mResponseTv = (TextView) findViewById(R.id.et_categoria);
        TextView titulo = (TextView) findViewById(R.id.titulo);
        Button create = (Button) findViewById(R.id.btn_tarea);
        TextView completadoOrigin = (TextView) findViewById(R.id.et_completado);
        btnOrigin = (TextView) findViewById(R.id.et_nombre);
        Button logout = (Button) findViewById(R.id.cerrar_sesion);

        /*
        * Aqui se inicializan todas las variales creadas arriba, agregando dos TextView que son el titulo,
        * Y tambien el boton para seguir creando las tareas.
        */
        completadoOrigin.setVisibility(View.GONE);
        titulo.setVisibility(View.VISIBLE); // Hago visible el titulo
        create.setVisibility(View.VISIBLE); // Hago visible el boton de crear
        logout.setVisibility(View.VISIBLE); // Hago visible el boton de cerrar sesion
                                             //////////////////////////////////////////////////////
        btnOrigin.setVisibility(View.GONE); // Quito al TextView original de las tareas         //
                                           // Esto es debido a que, de este TextView es donde  //
                                          //      se sacan los otros TextView.                //
                                         //////////////////////////////////////////////////////
        verBienvenida(name); // Se le da la bienvenida al usuario
 
        if(tamano != 0) // Si existen tareas en el sistema se empiezan a imprimir las tareas
        inflarLayout(nombre, categoria, completado);
        else { // Sino, se manda un aviso de que se deben crear las tareas
            mResponseTv.setBackgroundColor(Color.WHITE);
            mResponseTv.setTextColor(Color.BLACK);
            mResponseTv.setText("Lista de tareas vacia. Escriba su primera tarea :)");
        }


        /*
        * Luego de esto, si existen las tareas, se puede comenzar a modificar las que sean existentes
        * o eliminarse, aqui en btn, nos dice que si toco algun TextView que este dentro del contenedor
        * pasare al activity de Block_task, encargado de modificar o eliminar las tareas
        */

        if(tamano != 0)
        for(int i=0; i<tamano; i++) {
            final int finalI = i;
            if(layoiut[i] != null)
                layoiut[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ListSong = new Intent(getApplicationContext(), Block_task.class);
                        ListSong.putExtra("peticion",1);
                        ListSong.putExtra("variable_string", token);                      //////////////////////////////
                        ListSong.putExtra("name", name);                                 // Paso todo lo importante  //
                        ListSong.putExtra("username", username);                        // De la tareas a Block_task//
                        ListSong.putExtra("nombre", nombre[finalI]);                   // incluyendo una variable  //
                        ListSong.putExtra("categoria", categoria[finalI]);            // "boleana", que me dice   //
                        ListSong.putExtra("descripcion", descripcion[finalI]);       // el tipo de peticion que  //
                        ListSong.putExtra("id", id[finalI]);                        // se activaran en la otra  //
                        ListSong.putExtra("completado", completado[finalI]);       //         ventana.         //
                        ListSong.putExtra("fechaLimite", fechaLimite[finalI]);    //////////////////////////////
                        startActivity(ListSong);                                   
                        finish();                                                 
                    }
                });
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ListSong = new Intent(getApplicationContext(), Block_task.class);
                ListSong.putExtra("peticion",0);
                ListSong.putExtra("variable_string", token);    ////////////////////////////////////                 
                ListSong.putExtra("name", name);               //Aqui hago lo mismo que arriba   //
                ListSong.putExtra("username", username);      // Lo unico distinto, es que como //          
                ListSong.putExtra("nombre", "");             // no hay tareas existentes, los  //
                ListSong.putExtra("categoria", "");         // datos de una tareas se pasan   //
                ListSong.putExtra("descripcion", "");      //         vacios                 //
                ListSong.putExtra("id", "");              ////////////////////////////////////
                ListSong.putExtra("completado", false);
                ListSong.putExtra("fechaLimite", "");
                startActivity(ListSong);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ListSong = new Intent(getApplicationContext(), Logout.class);
                ListSong.putExtra("peticion", 1);
                ListSong.putExtra("variable_string", token);
                ListSong.putExtra("name", name);                      ////////////////////////////////////
                ListSong.putExtra("username", username);             // Al momento de tocar esta opcion//
                ListSong.putExtra("tamano", tamano);                // Se pasan los datos de task para//
                ListSong.putExtra("descripcion", descripcion);     // que no se pierdan si se decide //
                ListSong.putExtra("categoria", categoria);        // Regresar o cancelar el cerra-  //
                ListSong.putExtra("id", id);                     //         do de sesion           //
                ListSong.putExtra("nombre", nombre);            ////////////////////////////////////
                ListSong.putExtra("completado", completado);
                ListSong.putExtra("fechaLimite", fechaLimite);
                startActivity(ListSong);
                finish();
            }
        });

    }

    public void verBienvenida(String name){
        if(mWelcome.getVisibility()== View.GONE) {
             mWelcome.setVisibility(View.VISIBLE); //Se envia el mensaje de bienvenida al usuario
        }
        mWelcome.setText(name);
    }

    public void inflarLayout(String[] nombre, String[] category, boolean[] completado){

              ////////////////////////////////////////////////////////////////////////////////////////////////
             // Este es un metodo muy importante, ya que es el que se encarga de inflar el layout de Task  //
            // Para que se vayan creando las tareas existente, a medida que se van creando los TextView   //
           // Se les pasa el dato de la categoria y el nombre de la tarea para que se impriman en        //
          // pantalla, luego de eso se crea un nuevo contenedor y asi sucesivamente hasta acabar        //
         //                                      el ciclo.                                             //
        ////////////////////////////////////////////////////////////////////////////////////////////////

        mResponseTv.setVisibility(View.GONE);
        layout = (LinearLayout)findViewById(R.id.layout);
        for(int i = 0; i < tamano; i++) {
            contenedorBoton[i] = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.task, null);
            TextView categoria = (TextView) contenedorBoton[i].findViewById(R.id.et_categoria);
            TextView listo = (TextView) contenedorBoton[i].findViewById(R.id.et_completado);

            if(completado[i]){
                listo.setBackgroundColor(0xD815A312);
                listo.setText("Completado");
            }
            else
            {
                listo.setBackgroundColor(Color.RED);
                listo.setText("No Completado");
            }

            btn[i] = (TextView) contenedorBoton[i].findViewById(R.id.et_nombre);
            layoiut[i] = (LinearLayout) contenedorBoton[i].findViewById(R.id.layout);
            categoria.setText(category[i]);
            btn[i].setText(nombre[i]);
            layout.addView(contenedorBoton[i]);
        }

    }

    @Override
    public void onBackPressed() {
        Intent ListSong = new Intent(getApplicationContext(), Logout.class);
        ListSong.putExtra("peticion", 0);
        ListSong.putExtra("variable_string", token);
        ListSong.putExtra("name", name);
        ListSong.putExtra("username", username);
        ListSong.putExtra("tamano", tamano);
        ListSong.putExtra("descripcion", descripcion);
        ListSong.putExtra("categoria", categoria);
        ListSong.putExtra("id", id);
        ListSong.putExtra("nombre", nombre);
        ListSong.putExtra("completado", completado);
        ListSong.putExtra("fechaLimite", fechaLimite);
        startActivity(ListSong);
        finish();
    }
}