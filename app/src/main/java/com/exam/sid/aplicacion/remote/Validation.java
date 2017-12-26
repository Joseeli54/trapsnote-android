package com.exam.sid.aplicacion.remote;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Elias Barrientos on 12/25/2017.
 */

public class Validation {

    public int LoginNoNulo(TextView email, TextView password){
    int Logica = 0;
        if (!TextUtils.isEmpty(email.getText().toString())
                && !TextUtils.isEmpty(password.getText().toString())) {
            Logica = 1;
        }
        else{
            if (!TextUtils.isEmpty(email.getText().toString())
                    && TextUtils.isEmpty(password.getText().toString())) {
                Logica = 2;
            }
            else if (TextUtils.isEmpty(email.getText().toString())
                    && !TextUtils.isEmpty(password.getText().toString())) {
                Logica = 3;
            }
            else{
                Logica = 4;
            }
        }

        return Logica;
        }
}
