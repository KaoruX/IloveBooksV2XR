package com.example.ilovebooksv2xr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    public static String LlaveHistoria;
    public static String LlaveUsuario;

    //Todo esto esta relacionado con la historia para el Books

    public static String contenidoHistoria;
    public static String autorHistoria;
    public static int ActivoHistoria;


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

}