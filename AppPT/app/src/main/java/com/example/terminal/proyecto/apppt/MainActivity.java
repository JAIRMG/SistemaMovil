package com.example.terminal.proyecto.apppt;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.terminal.proyecto.apppt.MainActivity.ERROR_FROM_NETWORK_NOT_CONNECTED;
import static com.example.terminal.proyecto.apppt.MainActivity.FINAL_URL;
import static com.example.terminal.proyecto.apppt.MainActivity.SIGN_IN;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends AppCompatActivity  {

    //Facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    public Context context;
    public String userName = "";
    public String password = "";
    EditText passwordLogin;
    EditText nombreLogin;
    Button botonLogin;
    public String responseJson = "";
    //URL
    public static final String FINAL_URL = "http://192.168.1.74:8084/WebServiceForApp/webresources/generic";
    public static final String SIGN_IN = "/login/";
    public static final String ERROR_FROM_NETWORK_NOT_CONNECTED = "Error, Conexión a internet no disponible";



    public void click(View v) {
        Intent intent = null;
        switch(v.getId()) {
            case R.id.registrate: // R.id.textView1
                intent = new Intent(this, registro.class);
                break;
            case R.id.contraseña: // R.id.textView2
                intent = new Intent(this, password.class);
                break;
          /*  case R.id.button: // R.id.textView2
                intent = new Intent(this, Menu.class); //este es para acceder sin poner contraseña
                break;*/
        }
        startActivity(intent);
    }

    public void login() {
        Intent intent = null;
        intent = new Intent(this, registro.class);
        startActivity(intent);
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getApplicationContext();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        botonLogin = (Button) findViewById(R.id.button);
        nombreLogin = (EditText) findViewById(R.id.nombreLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
               login();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.com_facebook_smart_login_confirmation_cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        botonLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                userName = nombreLogin.getText().toString();
                password = passwordLogin.getText().toString();
                String finalJson = "";

                JSONObject userRequest = new JSONObject();
                try{
                    userRequest.put("userName", userName);
                    userRequest.put("password", password);
                    userRequest.put("email", userName);

                    finalJson = userRequest.toString();
                } catch(JSONException ex){
                    System.out.println("Error al crear JSON: "+ex);
                }


            // [{"userName":"jair","password":"1234","email":"jair.mg.27@gmail.com"}]

                new DoLogIn().execute(finalJson);

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private class DoLogIn extends AsyncTask<String, Long, String> {


        protected String doInBackground(String... data) {


            String jsonLogin = data[0].toString().replace("{","%7B").replace("}","%7D");
            jsonLogin = "["+jsonLogin+"]";
            Log.i("jsonFinal",jsonLogin);
            return HttpRequest.sendGetRequest(FINAL_URL + SIGN_IN + jsonLogin);
        }

        protected void onPostExecute(String response) {

            if(response==null || response.equals("")) {
                Toast.makeText(getApplicationContext(), "Error from web service", Toast.LENGTH_SHORT).show();
                /*mErrorLogin.setText(Palabras.ERROR_FROM_WEB_WERVICE);
                mProgressBar.setVisibility(View.GONE);
                mLoginContent.setVisibility(View.VISIBLE);*/
            }
            else if(response.equals(ERROR_FROM_NETWORK_NOT_CONNECTED)) {
                Toast.makeText(getApplicationContext(), "ERROR_FROM_NETWORK_NOT_CONNECTED", Toast.LENGTH_SHORT).show();
                /*mErrorLogin.setText(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
                mProgressBar.setVisibility(View.GONE);
                mLoginContent.setVisibility(View.VISIBLE);*/
            }
            else if(response.contains("Error") && response.contains("Message")) {
                try {
                    JSONObject json = new JSONObject(response);
                    int codeError = json.getInt("Error");
                    String message = json.getString("Message");
                    Toast.makeText(getApplicationContext(), "error: "+message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //mProgressBar.setVisibility(View.GONE);
                //mLoginContent.setVisibility(View.VISIBLE);
            }
            else{ //if(JSONBuilder.checkJsonStructure(response)) {
                Log.i("MyTAG: ", response);
                CheckIfCorrect(response);
                //goHome();
                /*new InfoHandler(getContext()).savePatientAndToken(response);
                Paciente paciente = new InfoHandler(getContext()).getPatientInfo();
                if(paciente.getEspecialista()!=null)
                    new GetSpetialistData().execute();
                else
                    new GetPatientSchedules().execute();*/
            }

        }

        public void CheckIfCorrect(String response){

            String jsonObjects[] = new String[7];
            String userName = "";
            String password = "";
            String email = "";
            String localidad = "";
            String edad = "";
            String nombreCompleto = "";
            try{
                JSONArray array = new JSONArray(response);


                JSONObject jsonObj = array.getJSONObject(0);
                jsonObjects[0] = jsonObj.getString("userName");
                jsonObjects[1] = jsonObj.getString("password");
                jsonObjects[2] = jsonObj.getString("email");
                jsonObjects[3] = jsonObj.getString("localidad");
                jsonObjects[4] = jsonObj.getString("edad");
                jsonObjects[5] = jsonObj.getString("respuesta");
                jsonObjects[6] = jsonObj.getString("name");

            } catch(JSONException e){
                System.out.println("Error al parsear json "+ e);
            }
            Log.i("Respuesta: ", response);
            if(jsonObjects[5].equals("1")){
                userName = jsonObjects[0];
                password = jsonObjects[1];
                email = jsonObjects[2];
                localidad = jsonObjects[3];
                edad = jsonObjects[4];
                nombreCompleto = jsonObjects[6];
                //String[] userInfo = new String[]{userName,password,email,localidad,edad+nombreCompleto};
                List<String> userInfo = new ArrayList<String>();
                userInfo.add(userName);
                userInfo.add(password);
                userInfo.add(email);
                userInfo.add(localidad);
                userInfo.add(edad);
                userInfo.add(nombreCompleto);

                //Log.i("Respuesta: ", userInfo[0]);
                goHome(userInfo);


            } else {
                Toast.makeText(getApplicationContext(), "Usuario o contraseñas incorrectas", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void goHome(List<String> userInfo){
        Intent intent = null;
        intent = new Intent(MainActivity.this, Menu.class);
        intent.putStringArrayListExtra("userInfo", (ArrayList<String>) userInfo);
        startActivity(intent);

    }


}



