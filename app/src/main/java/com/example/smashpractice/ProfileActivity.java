package com.example.smashpractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.mongodb.client.model.Filters;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class ProfileActivity extends AppCompatActivity {

    String email;
    String gamerTag;
    String main;

    TextView header;
    TextView emailText;
    ImageView profilePicture;
    Button pictureB;
    Button logoutB;
    Button statsB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        header = findViewById(R.id.header);
        header.setText("");
        emailText = findViewById(R.id.emailText);
        profilePicture = findViewById(R.id.profilePic);
        pictureB = findViewById(R.id.pictureB);
        logoutB = findViewById(R.id.logoutB);
        statsB = findViewById(R.id.statsB);



        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo user2 = (UserInfo) getApplication();
                user2.clear();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        UserInfo user = (UserInfo) getApplication();
        email = user.getEmail();
        emailText.setText(email);
        while(header.getText() == "")
        {
            getData();
        }

    }

    public void sendData(String username)
    {
        UserInfo user = (UserInfo) getApplication();
        user.setUserName(username);
    }

    public void getData() {
        RemoteFindIterable<Document> results;
        // Connect to MongoDB Client
        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");
        results = coll.find(Filters.eq("Email", email))
                .projection(
                        new Document());
        results.forEach(item -> {
            try {
                JSONObject obj = new JSONObject(item.toJson());
                gamerTag = obj.getString("GamerTag");
                header.setText(gamerTag + "'s Profile");
                sendData(gamerTag);
            } catch (JSONException e) {
                Log.d("JSON exception:", e.toString());
            }
        });
    }


}
