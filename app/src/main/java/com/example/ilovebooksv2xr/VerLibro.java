package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class VerLibro extends AppCompatActivity {

    FloatingActionButton fab;
    TextView autor;
    TextView titulo;
    TextView historia;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_libro);

        fab = findViewById(R.id.fab);
        autor = findViewById(R.id.verLibroAutor);
        titulo = findViewById(R.id.verLibroTitulo);
        historia = findViewById(R.id.verLibroHistoria);

        autor.setText(MainActivity.autorHistoria);
        titulo.setText(MainActivity.LlaveHistoria);
        historia.setText(MainActivity.contenidoHistoria);

        inicializarFirebase();
        ValidarVistoUsuario();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerLibro.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ValidarVistoUsuario(){
        databaseReference.child("UsuarioHistoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean aux = true;
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    UserHistoria h = objSnaptshot.getValue(UserHistoria.class);

                    if(MainActivity.LlaveHistoria.equals(h.getHistoria()) && MainActivity.LlaveUsuario.equals(h.getLector()) ){
                        aux = false;
                        break;
                    }
                }
                if(aux){
                    UserHistoria us = new UserHistoria();

                    us.setId(UUID.randomUUID().toString());
                    us.setLector(MainActivity.LlaveUsuario);
                    us.setHistoria(MainActivity.LlaveHistoria);
                    databaseReference.child("UsuarioHistoria").child(us.getId()).setValue(us);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}