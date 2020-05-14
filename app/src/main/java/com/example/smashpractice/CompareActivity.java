package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class CompareActivity extends AppCompatActivity {

    Spinner oneSpin;
    Spinner twoSpin;
    Spinner compSpin;
    Button compButton;
    Button addNoteButton;
    EditText newNoteText;

    String charOne;
    String charTwo;
    String comparable;
    String email;
    UserInfo user;

    double oneData;
    double twoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        user = (UserInfo) getApplication();
        email = user.getEmail();

        oneSpin = findViewById(R.id.charOne);
        twoSpin = findViewById(R.id.charTwo);
        compSpin = findViewById(R.id.compareSpin);
        compButton = findViewById(R.id.compareB);
        addNoteButton = findViewById(R.id.addNoteButton);
        newNoteText = findViewById(R.id.newNoteText);

        oneData = 0;
        twoData = 0;

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CompareActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(CompareActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.comparables));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oneSpin.setAdapter(myAdapter);
        twoSpin.setAdapter(myAdapter);
        compSpin.setAdapter(myAdapter2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.compare_nav);

        //Perform ItemSelectedList
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.compare_nav:
//                        startActivity(new Intent(getApplicationContext()
//                                ,CompareActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.record_nav:
                        //finish();
                        startActivity(new Intent(getApplicationContext()
                                , RecordOneActivity.class));
                        overridePendingTransition(0, 0);
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

        addNoteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addNote();
            }
        });


        compButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                compare();
            }
        });
    }

    public void compare() {
        while(oneData == 0 || twoData == 0) {
            charOne = oneSpin.getSelectedItem().toString();
            charTwo = twoSpin.getSelectedItem().toString();
            comparable = compSpin.getSelectedItem().toString();

            RemoteFindIterable<Document> results;
            RemoteFindIterable<Document> results2;
            // Connect to MongoDB Client
            final RemoteMongoCollection<Document> coll =
                    mongoClient.getDatabase("Characters").getCollection("Chars");
            results = coll.find(Filters.eq("Name", charOne))
                    .projection(
                            new Document());
            results.forEach(item -> {
                try {
                    JSONObject obj = new JSONObject(item.toJson());
                    double one = obj.getDouble(comparable);
                    oneData = one;
                } catch (JSONException e) {
                    Log.d("JSON exception:", e.toString());
                }
            });
            results2 = coll.find(Filters.eq("Name", charTwo))
                    .projection(
                            new Document());
            results2.forEach(item -> {
                try {
                    JSONObject obj = new JSONObject(item.toJson());
                    double two = obj.getDouble(comparable);
                    twoData = two;
                } catch (JSONException e) {
                    Log.d("JSON exception:", e.toString());
                }
            });
        }
        if(oneData > twoData) {
            Log.d("Result", "Winner: " + charOne + " With: " + oneData);
            Log.d("Result", "Loser: " + charTwo + " With: " + twoData);
            openResult();
            sendData(charOne, charTwo, comparable, oneData, twoData);
            finish();
        } else if(twoData > oneData) {
            Log.d("Result", "Winner: " + charTwo + " With: " + twoData);
            Log.d("Result", "Loser: " + charOne + " With: " + oneData);
            openResult();
            sendData(charTwo, charOne, comparable, twoData, oneData);
            finish();
        }else {
            Log.d("Result", "We have a tie! Both are : " + oneData);
            openResult();
            sendData(charOne, charTwo, comparable, oneData, twoData);
            finish();
        }

    }

    public void addNote() {
        if(TextUtils.isEmpty(newNoteText.getText().toString())) {
            Toast.makeText(getBaseContext(), "You need to enter some text!", Toast.LENGTH_SHORT).show();
        } else {
            String text = newNoteText.getText().toString();
            final RemoteMongoCollection<Document> coll =
                    mongoClient.getDatabase("UserData").getCollection("Notes");

            Document doc = new Document()
                    .append("Char_used", "NA")
                    .append("Char_fought", "NA")
                    .append("Result", "NA")
                    .append("Note", text)
                    .append("User_email", email);
            final Task<RemoteInsertOneResult> insert = coll.insertOne(doc);
            insert.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull Task<RemoteInsertOneResult> task) {
                    if (task.isSuccessful()) {
                        Log.i("STITCH", String.format("success inserting: %s",
                                task.getResult().getInsertedId()));
                    } else {
                        Log.e("app", "Failed to insert Document", task.getException());
                    }
                }
            });
        }
        newNoteText.setText("");
    }

    public void openResult() {
        Intent intent = new Intent(this, CompareResultActivity.class);
        startActivity(intent);
    }

    public void sendData(String winner, String loser, String comparable, double winnerData, double loserData) {
        Intent intent = new Intent(getApplicationContext(), CompareResultActivity.class);
        intent.putExtra("Winner", winner);
        intent.putExtra("Loser", loser);
        intent.putExtra("Comparable", comparable);
        intent.putExtra("WinnerData", winnerData);
        intent.putExtra("LoserData", loserData);
        startActivity(intent);
    }
}
