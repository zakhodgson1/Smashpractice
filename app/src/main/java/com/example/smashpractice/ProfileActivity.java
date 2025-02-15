package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mongodb.client.model.Filters;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class ProfileActivity extends AppCompatActivity {

    String email;
    String main;
    String tag;

    TextView header;
    TextView emailText;
    TextView address;
    ImageView profilePicture;
    Button mainB;
    Button logoutB;
    Button notesB;
    Button refreshB;

    UserInfo user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        header = findViewById(R.id.header);
        emailText = findViewById(R.id.emailText);
        profilePicture = findViewById(R.id.profilePic);
        mainB = findViewById(R.id.mainB);
        logoutB = findViewById(R.id.logoutB);
        notesB = findViewById(R.id.notesB);
        refreshB = findViewById(R.id.refreshB);
        address = findViewById(R.id.eAddress);

        user = (UserInfo) getApplication();
        email = user.getEmail();
        getTag();
        emailText.setText(email);
        tag = user.getUserName();
        header.setText(tag + "'s Profile");


        //Initalize and Assign Value
        BottomNavigationView bottomNavigationView2 = findViewById(R.id.bottom_navigation2);

        //Set home selected
        bottomNavigationView2.setSelectedItemId(R.id.profile_nav);

        //Perform ItemSelectedList
        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.profile_nav:
//                        finish();
//                        startActivity(new Intent(getApplicationContext()
//                                ,CalendarActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.play_nav:
                        finish();
                        startActivity(new Intent(getApplicationContext()
                                , PlayActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


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

        notesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserNotesActivity.class));
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
        tag = user.getUserName();
        email = user.getEmail();
        header.setText(tag + "'s Profile");
        emailText.setText(email);
        address.setText("Email Address:");
    }

    public void sendTag(String gtag) {
        tag = gtag;
        Log.d("debug", gtag);
        UserInfo user = (UserInfo) getApplication();
        user.setUserName(gtag);
    }

    public void sendMain(String umain) {
        main = umain;
        Log.d("debug", umain);
        UserInfo user = (UserInfo) getApplication();
        user.setMain(umain);
        setPicture(umain);
    }

    public void getTag() {
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
            String utag = obj.getString("GamerTag");
            sendTag(utag);
            String umain = obj.getString("Main");
            sendMain(umain);
        } catch (JSONException e) {
            Log.d("JSON exception:", e.toString());
        }
    });
}



    public void setPicture(String userMain)
    {
        switch(userMain)
        {
            case "Banjo" :
                profilePicture.setImageResource(R.drawable.banjo);
                break;

            case "Bayonetta" :
                profilePicture.setImageResource(R.drawable.bayo);
                break;

            case "Bowser" :
                profilePicture.setImageResource(R.drawable.bowser);
                break;

            case "Bowser jr" :
                profilePicture.setImageResource(R.drawable.bjr);
                break;

            case "Byleth" :
                profilePicture.setImageResource(R.drawable.byleth);
                break;

            case "Captain Falcon" :
                profilePicture.setImageResource(R.drawable.falcon);
                break;

            case "Chrom" :
                profilePicture.setImageResource(R.drawable.chrom);
                break;

            case "Cloud" :
                profilePicture.setImageResource(R.drawable.cloud);
                break;

            case "Corrin" :
                profilePicture.setImageResource(R.drawable.corrin);
                break;

            case "Daisy" :
                profilePicture.setImageResource(R.drawable.daisy);
                break;

            case "Dark Pit" :
                profilePicture.setImageResource(R.drawable.dpit);
                break;

            case "Dark Samus" :
                profilePicture.setImageResource(R.drawable.dsamus);
                break;

            case "Diddy Kong" :
                profilePicture.setImageResource(R.drawable.diddy);
                break;

            case "Doctor Mario" :
                profilePicture.setImageResource(R.drawable.doc);
                break;

            case "Donkey Kong":
                profilePicture.setImageResource(R.drawable.dk);
                break;

            case "Duck Hunt" :
                profilePicture.setImageResource(R.drawable.duckhunt);
                break;

            case "Falco" :
                profilePicture.setImageResource(R.drawable.falco);
                break;

            case "Fox" :
                profilePicture.setImageResource(R.drawable.fox);
                break;

            case "Game and Watch" :
                profilePicture.setImageResource(R.drawable.gnw);
                break;

            case "Ganondorf" :
                profilePicture.setImageResource(R.drawable.ganon);
                break;

            case "Greninja" :
                profilePicture.setImageResource(R.drawable.greninja);
                break;

            case "Hero" :
                profilePicture.setImageResource(R.drawable.hero);
                break;

            case "Ice Climbers" :
                profilePicture.setImageResource(R.drawable.ics);
                break;

            case "Ike" :
                profilePicture.setImageResource(R.drawable.ike);
                break;

            case "Incineroar" :
                profilePicture.setImageResource(R.drawable.incineroar);
                break;

            case "Inkling" :
                profilePicture.setImageResource(R.drawable.inkling);
                break;

            case "Isabelle" :
                profilePicture.setImageResource(R.drawable.isabelle);
                break;

            case "Jigglypuff" :
                profilePicture.setImageResource(R.drawable.jiggs);
                break;

            case "Joker" :
                profilePicture.setImageResource(R.drawable.joker);
                break;

            case "Ken" :
                profilePicture.setImageResource(R.drawable.ken);
                break;

            case "King Dedede" :
                profilePicture.setImageResource(R.drawable.dedede);
                break;

            case "King K Rool" :
                profilePicture.setImageResource(R.drawable.kkr);
                break;

            case "Kirby" :
                profilePicture.setImageResource(R.drawable.kirby);
                break;

            case "Link" :
                profilePicture.setImageResource(R.drawable.link);
                break;

            case "Little Mac" :
                profilePicture.setImageResource(R.drawable.mac);
                break;

            case "Lucario" :
                profilePicture.setImageResource(R.drawable.lucario);
                break;

            case "Lucas" :
                profilePicture.setImageResource(R.drawable.lucas);
                break;

            case "Lucina" :
                profilePicture.setImageResource(R.drawable.lucina);
                break;

            case "Luigi" :
                profilePicture.setImageResource(R.drawable.luigi);
                break;

            case "Mario" :
                profilePicture.setImageResource(R.drawable.mario);
                break;

            case "Marth" :
                profilePicture.setImageResource(R.drawable.marth);
                break;

            case "Mega Man" :
                profilePicture.setImageResource(R.drawable.mega);
                break;

            case "Meta Knight" :
                profilePicture.setImageResource(R.drawable.mk);
                break;

            case "Mewtwo" :
                profilePicture.setImageResource(R.drawable.mewtwo);
                break;

            case "Mii Brawler" :
                profilePicture.setImageResource(R.drawable.brawler);
                break;

            case "Mii Gunner" :
                profilePicture.setImageResource(R.drawable.gunner);
                break;

            case "Mii Swordfighter" :
                profilePicture.setImageResource(R.drawable.swordfighter);
                break;

            case "Ness" :
                profilePicture.setImageResource(R.drawable.ness);
                break;

            case "Olimar" :
                profilePicture.setImageResource(R.drawable.olimar);
                break;

            case "Pac Man" :
                profilePicture.setImageResource(R.drawable.pacman);
                break;

            case "Palutena" :
                profilePicture.setImageResource(R.drawable.palutena);
                break;

            case "Peach" :
                profilePicture.setImageResource(R.drawable.peach);
                break;

            case "Pichu" :
                profilePicture.setImageResource(R.drawable.pichu);
                break;

            case "Pikachu" :
                profilePicture.setImageResource(R.drawable.pikachu);
                break;

            case "Piranha Plant" :
                profilePicture.setImageResource(R.drawable.piranhaplant);
                break;

            case "Pit" :
                profilePicture.setImageResource(R.drawable.pit);
                break;

            case "Pokemon Trainer" :
                profilePicture.setImageResource(R.drawable.trainer);
                break;

            case "Richter" :
                profilePicture.setImageResource(R.drawable.richter);
                break;

            case "Ridley" :
                profilePicture.setImageResource(R.drawable.ridley);
                break;

            case "ROB" :
                profilePicture.setImageResource(R.drawable.rob);
                break;

            case "Robin" :
                profilePicture.setImageResource(R.drawable.robin);
                break;

            case "Rosalina" :
                profilePicture.setImageResource(R.drawable.rosa);
                break;

            case "Roy" :
                profilePicture.setImageResource(R.drawable.roy);
                break;

            case "Ryu" :
                profilePicture.setImageResource(R.drawable.ryu);
                break;

            case "Samus" :
                profilePicture.setImageResource(R.drawable.samus);
                break;

            case "Sheik" :
                profilePicture.setImageResource(R.drawable.sheik);
                break;

            case "Shulk" :
                profilePicture.setImageResource(R.drawable.shulk);
                break;

            case "Simon" :
                profilePicture.setImageResource(R.drawable.simon);
                break;

            case "Snake" :
                profilePicture.setImageResource(R.drawable.snake);
                break;

            case "Sonic" :
                profilePicture.setImageResource(R.drawable.sonic);
                break;

            case "Terry" :
                profilePicture.setImageResource(R.drawable.terry);
                break;

            case "Toon Link" :
                profilePicture.setImageResource(R.drawable.tink);
                break;

            case "Villager" :
                profilePicture.setImageResource(R.drawable.villager);
                break;

            case "Wario" :
                profilePicture.setImageResource(R.drawable.wario);
                break;

            case "Wii Fit Trainer" :
                profilePicture.setImageResource(R.drawable.wft);
                break;

            case "Wolf" :
                profilePicture.setImageResource(R.drawable.wolf);
                break;

            case "Yoshi" :
                profilePicture.setImageResource(R.drawable.yoshi);
                break;

            case "Young Link" :
                profilePicture.setImageResource(R.drawable.yink);
                break;

            case "Zelda" :
                profilePicture.setImageResource(R.drawable.zelda);
                break;

            case "Zero Suit Samus" :
                profilePicture.setImageResource(R.drawable.zss);
                break;

        }
    }


}
