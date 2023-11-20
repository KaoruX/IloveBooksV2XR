package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class mybooks extends AppCompatActivity {

    FloatingActionButton fab;
    private List<Historias> listhistorias = new ArrayList<Historias>();
    ArrayAdapter<Historias> arrayAdapterHistorias;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView mishistorias;
    private Button BCrearHistoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybooks);

        fab = findViewById(R.id.fab);
        mishistorias = findViewById(R.id.listViewmybooks);
        BCrearHistoria = findViewById(R.id.mybooksCrearHistoria);

        inicializarFirebase();
        MostrarHistorias();

        mishistorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Historias h = listhistorias.get(i); //El i Es posicion

                MainActivity.LlaveHistoria = h.getTitulo();
                MainActivity.contenidoHistoria = h.getHistoria();
                MainActivity.ActivoHistoria = h.getActivo();

                Intent per = new Intent(mybooks.this,EditarLibro.class);
                startActivity(per);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mybooks.this, MainActivity.class);
                startActivity(intent);
            }
        });

        BCrearHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mybooks.this, CrearLibro.class);
                startActivity(intent);
            }
        });

    }

    private void MostrarHistorias(){
        databaseReference.child("Historia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listhistorias.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Historias h = objSnaptshot.getValue(Historias.class);

                    if(h.getAutor().equals(MainActivity.LlaveUsuario)){
                        listhistorias.add(h);
                        arrayAdapterHistorias = new HistoriasAdapter(mybooks.this, listhistorias);
                        mishistorias.setAdapter(arrayAdapterHistorias);
                    }
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