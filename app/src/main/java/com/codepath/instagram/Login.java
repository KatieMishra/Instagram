package com.codepath.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button loginToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            loginToHome();
        } else {
            setContentView(R.layout.activity_main);

            usernameInput = (EditText) findViewById(R.id.etLoginUsername);
            passwordInput = (EditText) findViewById(R.id.etLoginPassword);
            loginBtn = findViewById(R.id.btnLogin);
            loginToSignup = findViewById(R.id.btnLoginToSignup);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    login(username, password);
                }
            });

            loginToSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent loginToSignup = new Intent(Login.this, SignUp.class);
                    startActivity(loginToSignup);
                    finish();
                }
            });
        }
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");
                    loginToHome();
                    finish();
                } else {
                    Log.e("LoginActivity", "Login error");
                    Toast.makeText(Login.this, "Login credentials incorrect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                }
            }
        });
    }

    private void loginToHome() {
        final Intent loginToHome = new Intent(Login.this, Home.class);
        startActivity(loginToHome);
    }

}
