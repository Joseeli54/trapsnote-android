package com.exam.sid.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Elias Barrientos on 12/5/2017.
 */

public class Task extends AppCompatActivity {

    TextView mResponseTv, mWelcome;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        Bundle datos = this.getIntent().getExtras();
        String token = datos.getString("variable_string");
        String name = datos.getString("variable_name");

        mResponseTv = (TextView) findViewById(R.id.tv_response);
        mWelcome = (TextView) findViewById(R.id.welcome);
        verBienvenida(name);
    }

    public void verBienvenida(String name){
        showWelcome("Bienvenido "+name);
    }

    public void verToken(String token){
        showResponse(token);
    }

    public void showResponse(String response) { //aqui hago visible el aviso de mensaje
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        else{
            mResponseTv.setVisibility(View.GONE);
        }
        mResponseTv.setText(response);
    }

    public void showWelcome(String response) { //aqui hago visible el aviso de mensaje
        mWelcome.setText(response);
    }

}