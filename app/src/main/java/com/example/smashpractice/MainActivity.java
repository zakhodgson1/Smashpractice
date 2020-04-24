package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize stitch client
        final StitchAppClient client =
                Stitch.initializeDefaultAppClient("smashpractice-jxlen");

        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "SmashPractice");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
