package com.exam.sid.aplicacion;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.sid.aplicacion.Objetos.Cuenta;
import com.exam.sid.aplicacion.Objetos.FirebaseReferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button buttonRegister;
    EditText editTextEmail, editTextPass, editTextName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button buttonRegister = (Button) findViewById(R.id.guardar_registro);
        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPass = (EditText) findViewById(R.id.register_password);

        buttonRegister.setOnClickListener(this);

    }

    private void registrar(String email, String pass) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("SESION", "usuario creado correctamente");
                    TextView IBMensaje = (TextView) findViewById(R.id.mensajelbl);
                    IBMensaje.setText("Usuario creado correctamente");
                } else {
                    Log.e("SESION", task.getException().getMessage() + "");
                }
            }
        });
    }

    public void ingresarDatos(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference cuentasRef = database.getReference(FirebaseReferences.CUENTAS_REFERENCE);

                String nameReg = editTextName.getText().toString();
                String emailReg = editTextEmail.getText().toString();
                String passReg = editTextPass.getText().toString();

                Cuenta cuenta = new Cuenta(nameReg, emailReg, passReg, 18);
                cuentasRef.child(FirebaseReferences.USUARIO_REFERENCE).push().setValue(cuenta);
    }


    @Override
    public void onClick(View view) {
        String emailReg = editTextEmail.getText().toString();
        String passReg = editTextPass.getText().toString();
        registrar(emailReg, passReg);
        ingresarDatos();
    }
}

