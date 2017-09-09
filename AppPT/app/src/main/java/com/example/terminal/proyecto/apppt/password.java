package com.example.terminal.proyecto.apppt;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class password extends AppCompatActivity {

    public Context context;
    Button botonEnviar;
    EditText campoCorreo;
    String correo="";


    public void sendToLogin(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        context = this;

        botonEnviar = (Button) findViewById(R.id.botonRecuperar);
        campoCorreo = (EditText) findViewById(R.id.campoCorreo);

        botonEnviar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                correo = campoCorreo.getText().toString();

                if(!correo.equals("") && correo.contains("@")){




                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(context, "Verifica que el correo sea correcto",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
