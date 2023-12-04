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

public class EditarLibro extends AppCompatActivity {

    FloatingActionButton fab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button ButtonEditar;
    private Button ButtonEliminar;
    private EditText TextViewTitulo;
    private EditText TextViewHistoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libro);

        fab = findViewById(R.id.fab);
        ButtonEditar = findViewById(R.id.editarLibroButtonEditar);
        ButtonEliminar = findViewById(R.id.editarLibroButtonEliminar);

        TextViewTitulo = findViewById(R.id.editarLibroTitulo);
        TextViewHistoria = findViewById(R.id.editarLibroHistoria);

        TextViewTitulo.setText(MainActivity.LlaveHistoria);
        TextViewHistoria.setText(MainActivity.contenidoHistoria);

        inicializarFirebase();

        ButtonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarHistoria();
            }
        });

        ButtonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarHistoria();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditarLibro.this, HistoriasDeUsuario.class);
                startActivity(intent);
            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void actualizarHistoria(){
        if(verificarCampos()){
            ValidarHistoriaExiste();
        }else{
            Toast.makeText(EditarLibro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void eliminarHistoria(){
        databaseReference.child("Historia").child(MainActivity.LlaveHistoria).removeValue();
        Toast.makeText(this, "La historia ha sido eliminada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditarLibro.this, HistoriasDeUsuario.class);
        startActivity(intent);
    }

    private boolean verificarCampos() {
        String titulo = TextViewTitulo.getText().toString();
        String contenido = TextViewHistoria.getText().toString();

        if (!titulo.isEmpty() && !contenido.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void ValidarHistoriaExiste(){
        databaseReference.child("Historia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Historias h = new Historias();
                String titulo = TextViewTitulo.getText().toString();
                String contenido = TextViewHistoria.getText().toString();

                if(titulo.equals(MainActivity.LlaveHistoria)){
                    h.setTitulo(titulo);
                    h.setHistoria(contenido);
                    h.setAutor(MainActivity.LlaveUsuario);
                    h.setActivo(1);

                    databaseReference.child("Historia").child(h.getTitulo()).setValue(h);

                    Toast.makeText(EditarLibro.this, "Historia Actualizada", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditarLibro.this, HistoriasDeUsuario.class);
                    startActivity(intent);
                }else{
                    boolean aux = false;
                    for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                        Historias a = objSnaptshot.getValue(Historias.class);
                        if(titulo.equals(a.getHistoria())){
                            aux = true;
                            break;
                        }
                    }
                    if(aux){
                        Toast.makeText(EditarLibro.this, "El titulo ya existe, Elige otro.", Toast.LENGTH_SHORT).show();
                    }else{
                        h.setTitulo(titulo);
                        h.setHistoria(contenido);
                        h.setAutor(MainActivity.LlaveUsuario);
                        h.setActivo(1);
                        //Actualizamos
                        databaseReference.child("Historia").child(h.getTitulo()).setValue(h);
                        //Eliminamos
                        databaseReference.child("Historia").child(MainActivity.LlaveHistoria).removeValue();

                        Intent intent = new Intent(EditarLibro.this, HistoriasDeUsuario.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}