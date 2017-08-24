package com.example.terminal.proyecto.apppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    //Facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public void click(View v) {
        Intent intent = null;
        switch(v.getId()) {
            case R.id.registrate: // R.id.textView1
                intent = new Intent(this, registro.class);
                break;
            case R.id.contraseña: // R.id.textView2
                intent = new Intent(this, password.class);
                break;
            case R.id.button: // R.id.textView2
                intent = new Intent(this, Menu.class);
                break;
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

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
