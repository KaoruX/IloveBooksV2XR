package com.example.ilovebooksv2xr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EditarLibro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libro);
    }

    public void menu(View v){
        Intent menu = new Intent(this,MainActivity.class);
        startActivity(menu);
    }
}