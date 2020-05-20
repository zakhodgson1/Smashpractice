package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    String main;

    ImageView mainPic;
    ImageView selectedPic;

    MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordone);

        TAG = "RecordOne";
        useSpinner = findViewById(R.id.useSpinner);
        useMain = findViewById(R.id.useMain);
        useSelected = findViewById(R.id.useSelected);

        mainPic = findViewById(R.id.mainPic);
        mainPic.setVisibility(View.INVISIBLE);
        selectedPic = findViewById(R.id.selectedPic);
        selectedPic.setVisibility(View.INVISIBLE);

        user = (UserInfo) getApplication();
        email = user.getEmail();
        main = user.getMain();

        showPic(main, mainPic);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RecordOneActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        useSpinner.setAdapter(myAdapter);

        useMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charInUse = user.getMain();
                playSound(charInUse);
                openNext(charInUse);
            }
        });

        useSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String character = useSpinner.getSelectedItem().toString();
                showPic(character, selectedPic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        useSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charInUse = useSpinner.getSelectedItem().toString();
                playSound(charInUse);
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

    public void showPic(String character, ImageView charPic) {
        switch (character) {
            case "Banjo":
                charPic.setImageResource(R.drawable.banjo);
                break;

            case "Bayonetta":
                charPic.setImageResource(R.drawable.bayo);
                break;

            case "Bowser":
                charPic.setImageResource(R.drawable.bowser);
                break;

            case "Bowser jr":
                charPic.setImageResource(R.drawable.bjr);
                break;

            case "Byleth":
                charPic.setImageResource(R.drawable.byleth);
                break;

            case "Captain Falcon":
                charPic.setImageResource(R.drawable.falcon);
                break;

            case "Chrom":
                charPic.setImageResource(R.drawable.chrom);
                break;

            case "Cloud":
                charPic.setImageResource(R.drawable.cloud);
                break;

            case "Corrin":
                charPic.setImageResource(R.drawable.corrin);
                break;

            case "Daisy":
                charPic.setImageResource(R.drawable.daisy);
                break;

            case "Dark Pit":
                charPic.setImageResource(R.drawable.dpit);
                break;

            case "Dark Samus":
                charPic.setImageResource(R.drawable.dsamus);
                break;

            case "Diddy Kong":
                charPic.setImageResource(R.drawable.diddy);
                break;

            case "Doctor Mario":
                charPic.setImageResource(R.drawable.doc);
                break;

            case "Donkey Kong":
                charPic.setImageResource(R.drawable.dk);
                break;

            case "Duck Hunt":
                charPic.setImageResource(R.drawable.duckhunt);
                break;

            case "Falco":
                charPic.setImageResource(R.drawable.falco);
                break;

            case "Fox":
                charPic.setImageResource(R.drawable.fox);
                break;

            case "Game and Watch":
                charPic.setImageResource(R.drawable.gnw);
                break;

            case "Ganondorf":
                charPic.setImageResource(R.drawable.ganon);
                break;

            case "Greninja":
                charPic.setImageResource(R.drawable.greninja);
                break;

            case "Hero":
                charPic.setImageResource(R.drawable.hero);
                break;

            case "Ice Climbers":
                charPic.setImageResource(R.drawable.ics);
                break;

            case "Ike":
                charPic.setImageResource(R.drawable.ike);
                break;

            case "Incineroar":
                charPic.setImageResource(R.drawable.incineroar);
                break;

            case "Inkling":
                charPic.setImageResource(R.drawable.inkling);
                break;

            case "Isabelle":
                charPic.setImageResource(R.drawable.isabelle);
                break;

            case "Jigglypuff":
                charPic.setImageResource(R.drawable.jiggs);
                break;

            case "Joker":
                charPic.setImageResource(R.drawable.joker);
                break;

            case "Ken":
                charPic.setImageResource(R.drawable.ken);
                break;

            case "King Dedede":
                charPic.setImageResource(R.drawable.dedede);
                break;

            case "King K Rool":
                charPic.setImageResource(R.drawable.kkr);
                break;

            case "Kirby":
                charPic.setImageResource(R.drawable.kirby);
                break;

            case "Link":
                charPic.setImageResource(R.drawable.link);
                break;

            case "Little Mac":
                charPic.setImageResource(R.drawable.mac);
                break;

            case "Lucario":
                charPic.setImageResource(R.drawable.lucario);
                break;

            case "Lucas":
                charPic.setImageResource(R.drawable.lucas);
                break;

            case "Lucina":
                charPic.setImageResource(R.drawable.lucina);
                break;

            case "Luigi":
                charPic.setImageResource(R.drawable.luigi);
                break;

            case "Mario":
                charPic.setImageResource(R.drawable.mario);
                break;

            case "Marth":
                charPic.setImageResource(R.drawable.marth);
                break;

            case "Mega Man":
                charPic.setImageResource(R.drawable.mega);
                break;

            case "Meta Knight":
                charPic.setImageResource(R.drawable.mk);
                break;

            case "Mewtwo":
                charPic.setImageResource(R.drawable.mewtwo);
                break;

            case "Mii Brawler":
                charPic.setImageResource(R.drawable.brawler);
                break;

            case "Mii Gunner":
                charPic.setImageResource(R.drawable.gunner);
                break;

            case "Mii Swordfighter":
                charPic.setImageResource(R.drawable.swordfighter);
                break;

            case "Ness":
                charPic.setImageResource(R.drawable.ness);
                break;

            case "Olimar":
                charPic.setImageResource(R.drawable.olimar);
                break;

            case "Pac Man":
                charPic.setImageResource(R.drawable.pacman);
                break;

            case "Palutena":
                charPic.setImageResource(R.drawable.palutena);
                break;

            case "Peach":
                charPic.setImageResource(R.drawable.peach);
                break;

            case "Pichu":
                charPic.setImageResource(R.drawable.pichu);
                break;

            case "Pikachu":
                charPic.setImageResource(R.drawable.pikachu);
                break;

            case "Piranha Plant":
                charPic.setImageResource(R.drawable.piranhaplant);
                break;

            case "Pit":
                charPic.setImageResource(R.drawable.pit);
                break;

            case "Pokemon Trainer":
                charPic.setImageResource(R.drawable.trainer);
                break;

            case "Richter":
                charPic.setImageResource(R.drawable.richter);
                break;

            case "Ridley":
                charPic.setImageResource(R.drawable.ridley);
                break;

            case "ROB":
                charPic.setImageResource(R.drawable.rob);
                break;

            case "Robin":
                charPic.setImageResource(R.drawable.robin);
                break;

            case "Rosalina":
                charPic.setImageResource(R.drawable.rosa);
                break;

            case "Roy":
                charPic.setImageResource(R.drawable.roy);
                break;

            case "Ryu":
                charPic.setImageResource(R.drawable.ryu);
                break;

            case "Samus":
                charPic.setImageResource(R.drawable.samus);
                break;

            case "Sheik":
                charPic.setImageResource(R.drawable.sheik);
                break;

            case "Shulk":
                charPic.setImageResource(R.drawable.shulk);
                break;

            case "Simon":
                charPic.setImageResource(R.drawable.simon);
                break;

            case "Snake":
                charPic.setImageResource(R.drawable.snake);
                break;

            case "Sonic":
                charPic.setImageResource(R.drawable.sonic);
                break;

            case "Terry":
                charPic.setImageResource(R.drawable.terry);
                break;

            case "Toon Link":
                charPic.setImageResource(R.drawable.tink);
                break;

            case "Villager":
                charPic.setImageResource(R.drawable.villager);
                break;

            case "Wario":
                charPic.setImageResource(R.drawable.wario);
                break;

            case "Wii Fit Trainer":
                charPic.setImageResource(R.drawable.wft);
                break;

            case "Wolf":
                charPic.setImageResource(R.drawable.wolf);
                break;

            case "Yoshi":
                charPic.setImageResource(R.drawable.yoshi);
                break;

            case "Young Link":
                charPic.setImageResource(R.drawable.yink);
                break;

            case "Zelda":
                charPic.setImageResource(R.drawable.zelda);
                break;

            case "Zero Suit Samus":
                charPic.setImageResource(R.drawable.zss);
                break;
        }
        charPic.setVisibility(View.VISIBLE);
    }


    public void playSound(String character) {

        switch (character) {
            case "Banjo":
                break;

            case "Bayonetta":
                break;

            case "Bowser":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.bowser);
                ring.start();
                break;

            case "Bowser jr":
                break;

            case "Byleth":
                break;

            case "Captain Falcon":
                break;

            case "Chrom":
                break;

            case "Cloud":
                break;

            case "Corrin":
                break;

            case "Daisy":
                break;

            case "Dark Pit":
                break;

            case "Dark Samus":
                break;

            case "Diddy Kong":
                break;

            case "Doctor Mario":
                break;

            case "Donkey Kong":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.donkey);
                ring.start();
                break;

            case "Duck Hunt":
                break;

            case "Falco":
                break;

            case "Fox":
                break;

            case "Game and Watch":
                break;

            case "Ganondorf":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.ganondorf);
                ring.start();
                break;

            case "Greninja":
                break;

            case "Hero":
                break;

            case "Ice Climbers":
                break;

            case "Ike":
                break;

            case "Incineroar":
                break;

            case "Inkling":
                break;

            case "Isabelle":
                break;

            case "Jigglypuff":
                break;

            case "Joker":
                break;

            case "Ken":
                break;

            case "King Dedede":
                break;

            case "King K Rool":
                break;

            case "Kirby":
                break;

            case "Link":
                break;

            case "Little Mac":
                break;

            case "Lucario":
                break;

            case "Lucas":
                break;

            case "Lucina":
                break;

            case "Luigi":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.luigi);
                ring.start();
                break;

            case "Mario":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.mario);
                ring.start();
                break;

            case "Marth":
                break;

            case "Mega Man":
                break;

            case "Meta Knight":
                break;

            case "Mewtwo":
                break;

            case "Mii Brawler":
                break;

            case "Mii Gunner":
                break;

            case "Mii Swordfighter":
                break;

            case "Ness":
                break;

            case "Olimar":
                break;

            case "Pac Man":
                break;

            case "Palutena":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.palu);
                ring.start();
                break;

            case "Peach":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.peach);
                ring.start();
                break;

            case "Pichu":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.pichu);
                ring.start();
                break;

            case "Pikachu":
                ring = MediaPlayer.create(RecordOneActivity.this, R.raw.pikachu);
                ring.start();
                break;

            case "Piranha Plant":
                break;

            case "Pit":
                break;

            case "Pokemon Trainer":
                break;

            case "Richter":
                break;

            case "Ridley":
                break;

            case "ROB":
                break;

            case "Robin":
                break;

            case "Rosalina":
                break;

            case "Roy":
                break;

            case "Ryu":
                break;

            case "Samus":
                break;

            case "Sheik":
                break;

            case "Shulk":
                break;

            case "Simon":
                break;

            case "Snake":
                break;

            case "Sonic":
                break;

            case "Terry":
                break;

            case "Toon Link":
                break;

            case "Villager":
                break;

            case "Wario":
                break;

            case "Wii Fit Trainer":
                break;

            case "Wolf":
                break;

            case "Yoshi":
                break;

            case "Young Link":
                break;

            case "Zelda":
                break;

            case "Zero Suit Samus":
                break;
        }
    }

}
