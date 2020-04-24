package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.Button;
import android.widget.TextView;

import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.emailInput) EditText emailInput;
    @InjectView(R.id.pwInput) EditText passwordInput;
    @InjectView(R.id.loginButton) Button loginButton;
    @InjectView(R.id.signupLink) TextView signupLink;
    @InjectView(R.id.pwLink) TextView pwLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
