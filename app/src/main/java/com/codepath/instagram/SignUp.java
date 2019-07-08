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
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private EditText phoneInput;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameInput = (EditText) findViewById(R.id.etUsername);
        passwordInput = (EditText) findViewById(R.id.etPassword);
        emailInput = (EditText) findViewById(R.id.etEmail);
        phoneInput = (EditText) findViewById(R.id.etPhone);
        signupBtn = findViewById(R.id.btnSignUp);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String email = emailInput.getText().toString();
                final String phone = phoneInput.getText().toString();

                createNewUser(username, password, email, phone);
            }
        });
    }

    public void createNewUser(final String username, final String password, String email, String phone) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        user.put("phone", phone);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.d("Signup", "Signup successful");
                    login(username, password);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e("Signup", "Signup error");
                    e.printStackTrace();
                }
            }
        });
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");
                    final Intent loginToHome = new Intent(SignUp.this, Home.class);
                    startActivity(loginToHome);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login error");
                    Toast.makeText(SignUp.this, "Login credentials incorrect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                }
            }
        });
    }
}
