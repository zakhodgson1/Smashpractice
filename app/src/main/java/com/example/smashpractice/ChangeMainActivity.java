package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.client.model.Filters;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class ChangeMainActivity extends AppCompatActivity {

    Spinner mainSpinner;
    Button enterButton;
    Button backButton;
    String email;
    String main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_main);

        UserInfo user = (UserInfo) getApplication();
        email = user.getEmail();

        mainSpinner = findViewById(R.id.mainSpinner);
        enterButton = findViewById(R.id.enterButton);
        backButton = findViewById(R.id.backButton);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ChangeMainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(myAdapter);

        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enterData();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goBack();
            }
        });

    }

    public void enterData() {
        main = mainSpinner.getSelectedItem().toString();

        Document filterDoc = new Document().append("Email", email);
        Document updateDoc = new Document().append("$set",
            new Document()
                .append("Main", main));

        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");

        final Task<RemoteUpdateResult> updateTask =
                coll.updateOne(filterDoc, updateDoc);
        updateTask.addOnCompleteListener(new OnCompleteListener<RemoteUpdateResult>() {
            @Override
            public void onComplete(@NonNull Task <RemoteUpdateResult> task) {
                if (task.isSuccessful()) {

                    Log.d("app", String.format("successfully modified document"));
                } else {
                    Log.e("app", "failed to update document with: ", task.getException());
                }
            }
        });
        goBack();
    }

    public void goBack() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

}
