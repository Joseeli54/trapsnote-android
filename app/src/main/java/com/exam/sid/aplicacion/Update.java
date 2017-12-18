package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.model.Patch;
import com.exam.sid.aplicacion.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update extends AppCompatActivity {
    private TextView mResponseTv;
    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final EditText name = (EditText) findViewById(R.id.et_name);
        final EditText last_name = (EditText) findViewById(R.id.et_last_name);
        final EditText password = (EditText) findViewById(R.id.et_password);

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Button btnActionUpdate = (Button) findViewById(R.id.btn_update);

        btnActionUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = username.getText().toString();
                Patch patch = new Patch(name.getText().toString(),
                        last_name.getText().toString());
                sendUpdate(nick, patch);
            }
        });
    }

    private void sendUpdate(String username, Patch patch) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<Patch> call = client.sendUpdate(username, patch);
        call.enqueue(new Callback<Patch>() {
            @Override
            public void onResponse(Call<Patch> call, Response<Patch> response) {
                showResponse("User Updated Successfull");
            }

            @Override
            public void onFailure(Call<Patch> call, Throwable t) {
                showResponse("Oh no! Error of server");
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