package com.example.ilovebooksv2xr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void perfil(View v){
        Intent per = new Intent(this,Perfil.class);
        startActivity(per);
    }

    public void books(View v){
        Intent book = new Intent(this,Books.class);
        startActivity(book);
    }

    public void buscador(View v){
        Intent bu = new Intent(this,Buscador.class);
        startActivity(bu);
    }

    public void aux1(View v){
        Intent aux = new Intent(this,Register.class);
        startActivity(aux);
    }

    public void aux2(View v){
        Intent aux = new Intent(this,Login.class);
        startActivity(aux);
    }

    public void CrearLibro(View v){
        Intent aux = new Intent(this,CrearLibro.class);
        startActivity(aux);
    }
    public void EditarLibro(View v){
        Intent aux = new Intent(this,EditarLibro.class);
        startActivity(aux);
    }
    public void VerLibro(View v){
        Intent aux = new Intent(this,VerLibro.class);
        startActivity(aux);
    }
    public void Admin(View v){
        Intent aux = new Intent(this,Admin.class);
        startActivity(aux);
    }

}