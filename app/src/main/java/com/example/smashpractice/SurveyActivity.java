package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static com.example.smashpractice.DatabaseHelper.client;
import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class SurveyActivity extends AppCompatActivity {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    String main;

    Spinner mainSpinner;
    Button enterButton;

    //Strings passed from signup
    String gTag;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Intent intent = getIntent();
        gTag = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        mainSpinner = findViewById(R.id.mainSpinner);
        enterButton = findViewById(R.id.enterButton);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SurveyActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(myAdapter);


        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enterData();
            }
        });
    }

    public void goBackToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        Toast.makeText(this.getBaseContext(),"Please verify the email " + email, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void enterData() {
        main = mainSpinner.getSelectedItem().toString();
        insertUser(gTag, email, password, main);
    }

    public void insertUser(String g_tag, String e_mail, String pw, String main) {

        try {
            pw = encrypt(pw);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");


        Document doc = new Document()
                .append("GamerTag", g_tag)
                .append("Email", e_mail)
                .append("Password", pw)
                .append("Main", main);

        final Task<RemoteInsertOneResult> insert = coll.insertOne(doc);

        insert.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<RemoteInsertOneResult> task) {
                if (task.isSuccessful()) {
                    Log.i("STITCH", String.format("success inserting: %s",
                            task.getResult().getInsertedId()));
                    goBackToLogin();
                } else {
                    Log.e("app", "Failed to insert Document", task.getException());
                }
            }
        });
    }

    public static String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(SurveyActivity.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;
    }
    private static Key generateKey() throws Exception
    {
        Key key = new SecretKeySpec(SurveyActivity.KEY.getBytes(),SurveyActivity.ALGORITHM);
        return key;
    }
}
