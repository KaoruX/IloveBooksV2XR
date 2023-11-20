package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Books extends AppCompatActivity {

    FloatingActionButton fab;
    private List<Historias> listhistorias = new ArrayList<Historias>();
    ArrayAdapter<Historias> arrayAdapterHistorias;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView historias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        historias = findViewById(R.id.listViewBooks);
        fab = findViewById(R.id.fab);

        inicializarFirebase();
        MostrarHistorias();

        historias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Historias h = listhistorias.get(i); //El i Es posicion

                MainActivity.LlaveHistoria = h.getTitulo();
                MainActivity.contenidoHistoria = h.getHistoria();
                MainActivity.autorHistoria = h.getAutor();

                Intent per = new Intent(Books.this,VerLibro.class);
                startActivity(per);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Books.this, MainActivity.class);
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
                    if(h.getActivo() != 0){
                        listhistorias.add(h);
                        arrayAdapterHistorias = new HistoriasAdapter(Books.this, listhistorias);
                        historias.setAdapter(arrayAdapterHistorias);
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