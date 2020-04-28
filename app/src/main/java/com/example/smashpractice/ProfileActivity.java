package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.mongodb.client.model.Filters;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

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
    Button mainB;
    Button logoutB;
    Button statsB;
    Button refreshB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        header = findViewById(R.id.header);
        emailText = findViewById(R.id.emailText);
        profilePicture = findViewById(R.id.profilePic);
        mainB = findViewById(R.id.mainB);
        logoutB = findViewById(R.id.logoutB);
        statsB = findViewById(R.id.statsB);
        refreshB = findViewById(R.id.refreshB);

        UserInfo user = (UserInfo) getApplication();
        email = user.getEmail();
        emailText.setText(email);
        getData();

        refreshB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        mainB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangeMainActivity.class));
            }
        });


        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo user2 = (UserInfo) getApplication();
                user2.clear();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    public void refresh() {
        while(header.getText() == "")
        {
            header.setText(gamerTag);
        }
    }


    public void sendData(String gamerTAG, String userMain)
    {
        UserInfo user = (UserInfo) getApplication();
        user.setUserName(gamerTAG);
        user.setMain(userMain);
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
                main = obj.getString("Main");
                header.setText(gamerTag);
                sendData(gamerTag, main);
            } catch (JSONException e) {
                Log.d("JSON exception:", e.toString());
            }
        });
    }


}
