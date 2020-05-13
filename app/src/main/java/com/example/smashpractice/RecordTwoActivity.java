package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mongodb.client.model.Filters;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;

import org.bson.Document;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class RecordTwoActivity extends AppCompatActivity {

    Spinner selectedSpinner;
    Button winButton;
    Button loseButton;
    Button logButton;
    Button switchButton;
    EditText note;
    String charInUse;
    String charFought;
    String email;
    String TAG;
    String noteText;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordtwo);

        TAG = "RecordTwo";
        Intent data = getIntent();
        charInUse = data.getStringExtra("Character");
        email = data.getStringExtra("Email");
        result = "NA";

        selectedSpinner = findViewById(R.id.selectedSpinner);
        winButton = findViewById(R.id.winButton);
        loseButton = findViewById(R.id.loseButton);
        logButton = findViewById(R.id.logButton);
        switchButton = findViewById(R.id.switchButton);
        note = findViewById(R.id.noteBox);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RecordTwoActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedSpinner.setAdapter(myAdapter);

        loseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winButton.setBackgroundColor(Color.GRAY);
                loseButton.setBackgroundColor(Color.RED);
                result = "Lose";
            }
        });

        winButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loseButton.setBackgroundColor(Color.GRAY);
                winButton.setBackgroundColor(Color.RED);
                result = "Win";
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logGame();
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecordOneActivity.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.record_nav);
        //Perform ItemSelectedList
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.compare_nav:
                        startActivity(new Intent(getApplicationContext()
                                ,CompareActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.record_nav:
                        //finish();
//                        startActivity(new Intent(getApplicationContext()
//                                , RecordOneActivity.class));
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.play_nav:
                        // finish();
                        startActivity(new Intent(getApplicationContext()
                                , PlayActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.watch_nav:
                        //finish();
                        startActivity(new Intent(getApplicationContext()
                                , WatchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void logGame() {
        Log.d(TAG, "in the log method");
        boolean validated = validate();
        if(validated == false) {
            Toast.makeText(this.getBaseContext(),"Choose a Result!", Toast.LENGTH_LONG).show();
            return;
        }
        charFought = selectedSpinner.getSelectedItem().toString();
        noteText = note.getText().toString();
        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Notes");

        Log.d(TAG, charInUse + " " + charFought + " " + result + " " + noteText + " " + email);
        Document doc = new Document()
                .append("Char_used", charInUse)
                .append("Char_fought", charFought)
                .append("Result", result)
                .append("Note", noteText)
                .append("User_email", email);
        final Task<RemoteInsertOneResult> insert = coll.insertOne(doc);
        insert.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<RemoteInsertOneResult> task) {
                if (task.isSuccessful()) {
                    Log.i("STITCH", String.format("success inserting: %s",
                            task.getResult().getInsertedId()));
                    clearData();
                } else {
                    Log.e("app", "Failed to insert Document", task.getException());
                    clearData();
                }
            }
        });
    }

    public Boolean validate() {
        if(result == "NA") {
            return false;
        }else {
            return true;
        }
    }


    public void clearData() {
        winButton.setBackgroundColor(Color.GRAY);
        loseButton.setBackgroundColor(Color.GRAY);
        result = "NA";
        note.setText("");
    }


}
