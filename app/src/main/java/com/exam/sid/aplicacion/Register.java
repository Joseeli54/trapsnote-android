package com.exam.sid.aplicacion;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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



    private TextView mResponseTv;
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";



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

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnActionRegister = (Button) findViewById(R.id.guardar_registro);

        btnActionRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (name.length()>5) {
                    Toast.makeText(getApplicationContext(), "ERROR-Nombre mayor a 50 caracteres", Toast.LENGTH_SHORT).show();
                    name.setText("");
                }
                else if (last_name.length()>5) {
                    Toast.makeText(getApplicationContext(), "ERROR-Apellido mayor a 50 caracteres", Toast.LENGTH_SHORT).show();
                    last_name.setText("");
                }
                else if (mes.getText().equals("") || username.getText().equals("") || name.getText().equals("") || last_name.getText().equals("") || email.getText().equals("") || password.getText().equals("") || dia.getText().equals("") || year.getText().equals("") ){
                    Toast.makeText(getApplicationContext(), "ERROR-Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
               /* else if () > 1999) {
                    Toast.makeText(getApplicationContext(), "ERROR-Debes ser mayor a 18 años", Toast.LENGTH_SHORT).show();
                    dia.setText("");
                    mes.setText("");
                    year.setText("");
                }
                else if (Integer.parseInt(dia.toString()) < 1 || Integer.parseInt(dia.toString()) > 31){
                    Toast.makeText(getApplicationContext(), "ERROR-introduzca un dia valido", Toast.LENGTH_SHORT).show();
                    dia.setText("");
                }
                else if (Integer.parseInt(mes.toString()) < 1 || Integer.parseInt(mes.toString()) > 12){
                    Toast.makeText(getApplicationContext(), "ERROR-introduzca un mes valido", Toast.LENGTH_SHORT).show();
                    mes.setText("");
                }*/



                String fecha = year.getText().toString()+"-"+
                        mes.getText().toString()+"-"+
                        dia.getText().toString();

                SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaProg = null;

                try {
                    fechaProg = sdfg.parse(fecha);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Post post = new Post(username.getText().toString(),
                        name.getText().toString(),last_name.getText().toString(),
                        email.getText().toString(), password.getText().toString(),
                        fechaProg);

                sendNetworkRequest(post);
            }
        });
    }

    private void sendNetworkRequest(Post post) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<Post> call = client.createAccount(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse("User Create Sucessfull");
                }
                else{
                    showResponse("We can't identify error");
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                showResponse("Error Service");
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

