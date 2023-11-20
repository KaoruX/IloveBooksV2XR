package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Buscador extends AppCompatActivity {

    FloatingActionButton fab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText historiaBuscada;
    private Button buscadorButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        fab = findViewById(R.id.fab);
        historiaBuscada = findViewById(R.id.search);
        buscadorButton = findViewById(R.id.buscadorButtonBuscar);

        buscadorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inicializarFirebase();
                verificarCampos();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Buscador.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ValidarHistoria(){
        databaseReference.child("Historia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Historias h = objSnaptshot.getValue(Historias.class);

                    if(h.getTitulo().equals(historiaBuscada.getText().toString()) && h.getActivo() != 0){

                        MainActivity.LlaveHistoria = h.getTitulo();
                        MainActivity.contenidoHistoria = h.getHistoria();
                        MainActivity.autorHistoria = h.getAutor();

                        Intent intent = new Intent(Buscador.this, VerLibro.class);
                        startActivity(intent);
                    }
                }
                Toast.makeText(Buscador.this, "Historia no existe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void verificarCampos() {
        String titulo = historiaBuscada.getText().toString();

        if (!titulo.isEmpty()) {
            ValidarHistoria();
        } else {
            Toast.makeText(this, "Rellena el campo", Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}