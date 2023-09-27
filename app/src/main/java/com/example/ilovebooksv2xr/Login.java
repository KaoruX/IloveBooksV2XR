package com.example.ilovebooksv2xr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.CheckBox;
import android.widget.Toast;
public class Login extends AppCompatActivity {
    private Button b;
    private RatingBar ex;
    private CheckBox c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b = (Button) findViewById(R.id.b2);
        ex = (RatingBar) findViewById(R.id.rex);
        c = (CheckBox) findViewById(R.id.c1);

        ch();
    }
    public void menu(View v){
        Intent menu = new Intent(this,MainActivity.class);
        startActivity(menu);
    }

    public void ch(){
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this,"¡De Recordare! 7w7",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void extrellas(View v){
        Toast.makeText(Login.this,"Pusiste: "+ex.getRating()+"Arigato :3",Toast.LENGTH_LONG).show();
    }

}