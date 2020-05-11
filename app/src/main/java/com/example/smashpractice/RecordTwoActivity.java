package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecordTwoActivity extends AppCompatActivity {

    Spinner selectedSpinner;
    Button winButton;
    Button loseButton;
    Button logButton;
    Button switchButton;
    EditText note;
    String charInUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordtwo);

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
}
