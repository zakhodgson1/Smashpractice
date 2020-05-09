package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mongodb.client.model.Filters;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class CompareActivity extends AppCompatActivity {

    Spinner oneSpin;
    Spinner twoSpin;
    Spinner compSpin;
    Button compButton;

    String charOne;
    String charTwo;
    String comparable;

    double oneData;
    double twoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        oneSpin = findViewById(R.id.charOne);
        twoSpin = findViewById(R.id.charTwo);
        compSpin = findViewById(R.id.compareSpin);
        compButton = findViewById(R.id.compareB);

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
//                                ,CalendarActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.record_nav:
                        //finish();
                        startActivity(new Intent(getApplicationContext()
                                , PlayActivity.class));
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
                                , PlayActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
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
