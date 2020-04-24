package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.client.model.Filters;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.core.auth.providers.userpassword.UserPasswordAuthProviderClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.userpassword.UserPasswordCredential;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.emailInput) EditText emailInput;
    @InjectView(R.id.pwInput) EditText passwordInput;
    @InjectView(R.id.loginButton) Button loginButton;
    @InjectView(R.id.signupLink) TextView signupLink;
    @InjectView(R.id.pwLink) TextView passwordLink;

    boolean loginAttempted;

    public static String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        passwordLink.setVisibility(View.INVISIBLE);
        loginAttempted = false;

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    // disable going back to the MainActivity
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void login() {
        if(!validate()) {
            onLoginFailed();
            return;
        }
        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        UserPasswordCredential credential = new UserPasswordCredential(email, password);
        Stitch.getDefaultAppClient().getAuth().loginWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<StitchUser>() {
                    @Override
                    public void onComplete(@NonNull final Task<StitchUser> task) {
                        if (task.isSuccessful()) {
                            RemoteFindIterable<Document> results;
                            // Connect to MongoDB Client
                            final RemoteMongoCollection<Document> coll =
                                    mongoClient.getDatabase("UserData").getCollection("Users");
                            results = coll.find(Filters.eq("email", email))
                                    .projection(
                                            new Document());
                            results.forEach(item -> {
                                try {
                                    JSONObject obj = new JSONObject(item.toJson());
                                    UserInfo user = (UserInfo) getApplication();
                                    userID =  obj.getJSONObject("_id").getString("$oid");
                                    user.setUserID(userID);
                                    Log.d("*******",userID);
                                } catch (JSONException e) {
                                    Log.d("JSON exception:", e.toString());
                                }
                            });

                            onLoginSuccess(email);
                            progressDialog.dismiss();
                        } else {
                            Log.e("stitch", "Error logging in with email/password auth:", task.getException());
                            progressDialog.dismiss();
                            passwordInput.setText("");
                            onLoginFailed();
                        }
                    }
                });
    }




    public void onLoginSuccess(String email) {
        loginButton.setEnabled(true);
        openProfileActivity();
        sendData(email);
        //openViewHabitActivity(); - where we are supposed to go
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);

        if(loginAttempted == false) {

            passwordLink.setVisibility(View.VISIBLE);
            passwordLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recoverPassword();
                }
            });
            loginAttempted = true;
        }
    }

    public void openProfileActivity() {

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void sendData(String email)
    {
        UserInfo user = (UserInfo) getApplication();
        user.setEmail(email);
        user.setUserID(userID);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

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

    private void recoverPassword() {

        String email = emailInput.getText().toString();

        UserPasswordAuthProviderClient emailPassClient = Stitch.getDefaultAppClient().getAuth().getProviderClient(UserPasswordAuthProviderClient.factory);

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("error! field is required!");
        } else {
            emailPassClient.sendResetPasswordEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull final Task<Void> task) {
                                                   if (task.isSuccessful()) {
                                                       Log.d("stitch-auth", "Successfully sent password reset email");
                                                       Toast.makeText(LoginActivity.this, "Password reset mail sent.", Toast.LENGTH_SHORT).show();
                                                   } else {
                                                       Log.e("stitch-auth", "Error sending password reset email:", task.getException());
                                                       Toast.makeText(LoginActivity.this, "Account not found!", Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           }
                    );
        }
    }
}
