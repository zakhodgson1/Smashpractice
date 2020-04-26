package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.auth.providers.userpassword.UserPasswordAuthProviderClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.Gtag) EditText username;
    @InjectView(R.id.emailInput) EditText emailInput;
    @InjectView(R.id.pwInput) EditText passwordInput;
    @InjectView(R.id.loginB) Button loginButton;
    @InjectView(R.id.signupB) Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }


        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String gamerTag = username.getText().toString();
        String email = emailInput.getText().toString().toLowerCase();
        String password = passwordInput.getText().toString();

        UserPasswordAuthProviderClient emailPassClient = Stitch.getDefaultAppClient().getAuth().getProviderClient(
                UserPasswordAuthProviderClient.factory
        );

        emailPassClient.registerWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull final Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   Log.i("stitch", "Successfully sent account confirmation email");
                                                   Toast.makeText(SignupActivity.this, "Email confirmation mail sent.", Toast.LENGTH_SHORT).show();
                                                   progressDialog.dismiss();
                                                   onSignupSuccess(gamerTag, email, password);

                                               } else {
                                                   Log.e("stitch", "Error registering new user:", task.getException());
                                                   progressDialog.dismiss();
                                                   onSignupFailed();
                                               }
                                           }
                                       }
                );
    }

    public boolean validate() {
        boolean valid = true;

        String gamerTag = username.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (gamerTag.isEmpty()) {
            username.setError("must be least 1 character");
            valid = false;
        } else {
            username.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("enter a valid email address");
            valid = false;
        } else {
            emailInput.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordInput.setError("must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordInput.setError(null);
        }
        return valid;
    }

    public void onSignupSuccess(String username, String email, String password) {

        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        openSurveyActivity();
        sendData(username, email, password);
        finish();
    }

    public void sendData(String name, String email, String password)
    {
        Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
        intent.putExtra("username", name);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public void openSurveyActivity(){
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }
}
