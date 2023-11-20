package com.example.ilovebooksv2xr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Perfil extends AppCompatActivity {

    TextView numero;
    FloatingActionButton fab;
    private ListView LibrosLeidos;
    private List<Historias> listhistorias = new ArrayList<Historias>();
    ArrayAdapter<Historias> arrayAdapterHistorias;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView TextUser;
    private Button ButtonMyBooks;
    private int cambiarNumero = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        numero = findViewById(R.id.textView3);
        TextUser = findViewById(R.id.idUser);
        fab = findViewById(R.id.fab);
        ButtonMyBooks = findViewById(R.id.perfilButtonMyBooks);

        LibrosLeidos = findViewById(R.id.listViewPerfil);

        TextUser.setText(MainActivity.LlaveUsuario);

        inicializarFirebase();
        MostrarLibrosLeidos();


        ButtonMyBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, mybooks.class);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void MostrarLibrosLeidos() {
        final List<Historias> librosLeidos = new ArrayList<>();

        databaseReference.child("Historia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                librosLeidos.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Historias h = objSnapshot.getValue(Historias.class);
                    ValidarLibrosLeidos(new OnBooksCheckedListener() {
                        @Override
                        public void onChecked(boolean isChecked) {
                            if (isChecked) {
                                librosLeidos.add(h);
                                cambiarNumero+=1;
                                numero.setText(String.valueOf(cambiarNumero));
                                arrayAdapterHistorias = new HistoriasAdapter(Perfil.this, librosLeidos);
                                LibrosLeidos.setAdapter(arrayAdapterHistorias);
                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void ValidarLibrosLeidos(final OnBooksCheckedListener listener) {
        databaseReference.child("UsuarioHistoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean encontrado = false;
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    UserHistoria h = objSnapshot.getValue(UserHistoria.class);
                    if (MainActivity.LlaveUsuario.equals(h.getLector())) {
                        encontrado = true;
                        break;
                    }
                }
                listener.onChecked(encontrado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error si ocurre
            }
        });
    }

    interface OnBooksCheckedListener {
        void onChecked(boolean isChecked);
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}