package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearLibro extends AppCompatActivity {

    FloatingActionButton fab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button crearLibro;
    private TextView titulo;
    private TextView contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libro);

        fab = findViewById(R.id.fab);

        crearLibro = findViewById(R.id.crearLibroButtonPublicar);
        titulo = findViewById(R.id.crearLibroTitulo);
        contenido = findViewById(R.id.crearLibroContenido);

        inicializarFirebase();
        crearLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarCamposVacios();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearLibro.this, HistoriasDeUsuario.class);
                startActivity(intent);
            }
        });
    }

    private void ValidarCamposVacios(){
        String tituloText = titulo.getText().toString();
        String contenidoText = contenido.getText().toString();

        if (tituloText.isEmpty() || contenidoText.isEmpty()) {
            Toast.makeText(CrearLibro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            ValidarHistoriaExiste(tituloText,contenidoText);
        }
    }

    private void ValidarHistoriaExiste(String tituloHistoria, String contenidoText){
        databaseReference.child("Historia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean aux = false;
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    UserHistoria h = objSnaptshot.getValue(UserHistoria.class);
                    if(tituloHistoria.equals(h.getHistoria())){
                        aux = true;
                        break;
                    }
                }
                if(aux){
                    Toast.makeText(CrearLibro.this, "El titulo ya existe, Elige otro.", Toast.LENGTH_SHORT).show();
                }else{
                    Historias historiaNueva = new Historias();
                    historiaNueva.setTitulo(tituloHistoria);
                    historiaNueva.setHistoria(contenidoText);
                    historiaNueva.setActivo(1);
                    historiaNueva.setAutor(MainActivity.LlaveUsuario);

                    databaseReference.child("Historia").child(tituloHistoria).setValue(historiaNueva);
                    Intent intent = new Intent(CrearLibro.this, MainActivity.class);
                    startActivity(intent);
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