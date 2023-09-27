package com.example.ilovebooksv2xr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;
import java.util.TimerTask;
import java.util.Timer;

public class Register extends AppCompatActivity {
    private RadioButton ra1,ra2;
    private ProgressBar p;
    int aux = 0;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ra1 = (RadioButton) findViewById(R.id.r1);
        ra2 = (RadioButton) findViewById(R.id.r2);
        p = (ProgressBar) findViewById(R.id.pbar);
        b = (Button) findViewById(R.id.b1);

        radios1();
        radios2();
        barra();
    }

    public void menu(View v){
        Intent menu = new Intent(this,MainActivity.class);
        startActivity(menu);
    }

    public void radios1(){
        ra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Register.this,"Eres una mujer :D",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void radios2(){
        ra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Register.this,"Eres un Hombre :D",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void barra(){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Timer t = new Timer();
                TimerTask tt = new TimerTask(){
                    @Override
                    public void run(){
                        aux++;
                        p.setProgress(aux);
                        if(aux==100)
                            t.cancel();
                    }
                };
                t.schedule(tt,0,100);
            }
        });
    }

}