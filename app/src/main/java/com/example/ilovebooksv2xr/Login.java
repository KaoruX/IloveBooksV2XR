package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button loginButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.search);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.loginButtonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inicializarFirebase();
                verificarCampos();

            }
        });

    }

    private void ValidarUsuarioLogin(){
        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    User p = objSnaptshot.getValue(User.class);

                    if(p.getUser().equals(user.getText().toString())){
                        if(p.getPassword().equals(password.getText().toString())){

                            MainActivity.LlaveUsuario = p.getUser();

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Login.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Toast.makeText(Login.this, "Usuario No existe", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void verificarCampos() {
        String username = user.getText().toString();
        String passwordA1 = password.getText().toString();

        if (!username.isEmpty() && !passwordA1.isEmpty()) {
                ValidarUsuarioLogin();
        } else {
            Toast.makeText(Login.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}