package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.model.Get;
import com.exam.sid.aplicacion.model.User;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity{

    private TextView mResponseTv;
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText name = (EditText) findViewById(R.id.et_name);
        final EditText last_name = (EditText) findViewById(R.id.et_last_name);
        final EditText email = (EditText) findViewById(R.id.et_email);
        final EditText password = (EditText) findViewById(R.id.et_password);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        Button createAccountButton = (Button) findViewById(R.id.btn_submit);
        Button btnGetList = (Button) findViewById(R.id.btn_verify);

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getList();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                User user = new User(
                        name.getText().toString(),
                        last_name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString());
                sendNetworkRequest(user);
            }
        });

    }

    private void getList() {

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        UserClient client = retrofit.create(UserClient.class);

        Call<Get> listCall = client.getList();

        listCall.enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {
                showResponse("Base de datos leida");
            }

            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                Log.e(getApplicationContext().toString(), t.getMessage());
            }
        });

    }

    private void sendNetworkRequest(User user) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<User> call = client.createAccount(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    showResponse("User Create");
                }
                else{
                    showResponse("We can't identify error");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
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

