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

public class RecordOneActivity extends AppCompatActivity {

    Spinner useSpinner;
    Button useMain;
    Button useSelected;

    String charInUse;
    UserInfo user;
    String TAG;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordone);

        TAG = "RecordOne";
        useSpinner = findViewById(R.id.useSpinner);
        useMain = findViewById(R.id.useMain);
        useSelected = findViewById(R.id.useSelected);

        user = (UserInfo) getApplication();
        email = user.getEmail();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RecordOneActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        useSpinner.setAdapter(myAdapter);

        useMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charInUse = user.getMain();
                openNext(charInUse);
            }
        });

        useSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charInUse = useSpinner.getSelectedItem().toString();
                openNext(charInUse);
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
                        startActivity(new Intent(getApplicationContext(), WatchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void openNext(String charUsed) {
        Log.d(TAG, charUsed);
        Intent intent = new Intent(getApplicationContext(), RecordTwoActivity.class);
        intent.putExtra("Character", charUsed);
        intent.putExtra("Email", email);
        startActivity(intent);
    }
}
