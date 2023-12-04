package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

public class Register extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private EditText password2;
    private Button registerButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<User> listPerson = new ArrayList<User>();
    ArrayList<User> arrayAdapterPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.search);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.passwordConfirm);
        registerButton = findViewById(R.id.registerButtonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inicializarFirebase();
                verificarCampos();

            }
        });


    }

    private void ValidarUsuario(){
        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean ban = false;

                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    User p = objSnaptshot.getValue(User.class);
                    if(p.getUser().equals(user.getText().toString())){
                        ban = false;
                        break;
                    }else{ ban = true; }

                }

                if(ban){
                    User nuevoUsuario = new User();
                    nuevoUsuario.setUser(user.getText().toString());
                    nuevoUsuario.setPassword(password.getText().toString());

                    databaseReference.child("Usuario").child(nuevoUsuario.getUser()).setValue(nuevoUsuario);

                    MainActivity.LlaveUsuario = nuevoUsuario.getUser();

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);

                } else{
                    Toast.makeText(Register.this, "Usuario Existe", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void verificarCampos() {
        String username = user.getText().toString();
        String passwordA1 = password.getText().toString();
        String passwordA2 = password2.getText().toString();

        if (!username.isEmpty() && !passwordA1.isEmpty() && !passwordA2.isEmpty()) {
            if(passwordA1.equals(passwordA2)){
                ValidarUsuario();
            }else{
                Toast.makeText(Register.this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Register.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}