package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {

    @InjectView(R.id.Gtag) EditText username;
    @InjectView(R.id.emailInput) EditText emailInput;
    @InjectView(R.id.pwInput) EditText passwordInput;
    @InjectView(R.id.loginB) Button loginButton;
    @InjectView(R.id.signupB) Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
