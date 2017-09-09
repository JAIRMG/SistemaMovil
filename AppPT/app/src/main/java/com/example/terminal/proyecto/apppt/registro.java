package com.example.terminal.proyecto.apppt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.data;

public class registro extends AppCompatActivity {

    public Context context;
    String nombre="",localidad="",apellido="",correo="",contrasenia1="",
           contrasenia2="",usuario="";
    Integer edad=0;
    EditText editNombre, editApellido, editCorreo, editContrasenia1,
             editContrasenia12, editUsuario;
    Spinner spinnerLocalidad, spinnerEdad;
    String[] estados = new String[]{"CDMX","Aguascalientes","Baja California",
            "Baja California Sur", "Campeche", "Chiapas", "Chihuahua",
            "Coahuila de Zaragoza", "Colima", "Colima", "Durango", "Guanajuato",
            "Guerrero", "Hidalgo", "Jalisco", "México", "Michoacán de Ocampo",
            "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro de Arteaga",
            "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora","Tabasco",
            "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"};
    Integer[] edades = new Integer[]{10,11,12,13,14,15,16};


    Button registrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        context = this;


        registrar = (Button) findViewById(R.id.botonRegistro);
        editNombre = (EditText) findViewById(R.id.nombre);
        editApellido = (EditText) findViewById(R.id.apellido);
        editUsuario = (EditText) findViewById(R.id.usuario);
        editCorreo = (EditText) findViewById(R.id.correo);
        editContrasenia1 = (EditText) findViewById(R.id.contrasenia1);
        editContrasenia12 = (EditText) findViewById(R.id.contrasenia2);
        spinnerLocalidad = (Spinner) findViewById(R.id.localidad);
        spinnerEdad = (Spinner) findViewById(R.id.edad);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, estados);
        spinnerLocalidad.setAdapter(adapter);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_dropdown_item, edades);
        spinnerEdad.setAdapter(adapter2);



        registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                nombre = editNombre.getText().toString();
                apellido = editApellido.getText().toString();
                usuario = editUsuario.getText().toString();
                correo = editCorreo.getText().toString();
                contrasenia1 = editContrasenia1.getText().toString();
                contrasenia2 = editContrasenia12.getText().toString();
                localidad = spinnerLocalidad.getSelectedItem().toString();
                edad = (Integer) spinnerEdad.getSelectedItem();

                Log.d("spinner","localidad: "+localidad + " edad: "+edad);

                InputValidatorHelper myInput = new InputValidatorHelper();


                if(!myInput.containsDigit(nombre) || !myInput.containsDigit(apellido)){
                    Toast.makeText(context, "El campo nombre y apellido solo pueden tener letras",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.isEmpty(nombre) || !myInput.isEmpty(apellido) ||
                !myInput.isEmpty(usuario) || !myInput.isEmpty(contrasenia1)){
                    Toast.makeText(context, "Campos vacíos",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.checkPass(contrasenia1,contrasenia2)){
                    Toast.makeText(context, "Contraseñas no coinciden",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.isValidEmail(correo)){
                    Toast.makeText(context, "Correo incorrecto",
                            Toast.LENGTH_LONG).show();
                }else{

                    altaAlumno();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    public void altaAlumno(){

    }

    public class InputValidatorHelper {

        public boolean isValidEmail(String string){
            if(string.contains("@")){
                return true;
            } else {
                return false;
            }
        }

        public boolean isEmpty(String string){

            if(string.equals("")){
                return false;
            }else{
               return true;
            }

        }

        public final boolean containsDigit(String s) {
            boolean containsDigit = false;

            if (s != null && !s.isEmpty()) {
                for (char c : s.toCharArray()) {
                    if (containsDigit = Character.isDigit(c)) {
                        break;
                    }
                }
            }

            return !containsDigit;
        }

        public boolean checkPass(String pass1, String pass2){
            if(pass1.equals(pass2)){
                return true;
            } else {
                return false;
            }

        }


    }

}
