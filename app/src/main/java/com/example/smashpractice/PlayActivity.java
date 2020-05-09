package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mongodb.lang.NonNull;

public class PlayActivity extends AppCompatActivity {

    Button recordButton;
    Button compareButton;
    Button watchButton;
    UserInfo user;
    String email;
    String id;
    String main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        recordButton = findViewById(R.id.record_b);
        compareButton = findViewById(R.id.compare_b);
        watchButton = findViewById(R.id.watch_b);

        BottomNavigationView bottomNavigationView2 = findViewById(R.id.bottom_navigation2);

        //Set home selected
        bottomNavigationView2.setSelectedItemId(R.id.play_nav);

        //Perform ItemSelectedList
        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.profile_nav:
                        finish();
                        startActivity(new Intent(getApplicationContext()
                                ,ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.play_nav:
//                        finish();
//                        startActivity(new Intent(getApplicationContext()
//                                , PlayActivity.class));
  //                      overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordActivity();
            }
        });

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCompareActivity();
            }
        });

        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWatchActivity();
            }
        });
    }


    public void openRecordActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openCompareActivity() {
        Intent intent = new Intent(this, CompareActivity.class);
        startActivity(intent);
    }

    public void openWatchActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }








}
