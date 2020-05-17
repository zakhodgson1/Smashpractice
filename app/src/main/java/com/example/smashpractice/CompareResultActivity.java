package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CompareResultActivity extends AppCompatActivity {

    TextView winnerText;
    TextView winnerData;
    TextView loserData;

    ImageView winnerPic;
    ImageView loserPic;

    Button backButton;

    String winner;
    String loser;
    String comparable;

    MediaPlayer ring;

    double wData;
    double lData;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_result);

        Intent intent = getIntent();
        winner = intent.getStringExtra("Winner");
        loser = intent.getStringExtra("Loser");
        comparable = intent.getStringExtra("Comparable");
        wData = intent.getDoubleExtra("WinnerData", 0);
        lData = intent.getDoubleExtra("LoserData", 0);

        count = 0;

        winnerText = findViewById(R.id.winnerTV);
        winnerData = findViewById(R.id.wdataTV);
        loserData = findViewById(R.id.ldataTV);
        winnerPic = findViewById(R.id.winnerPic);
        loserPic = findViewById(R.id.loserPic);
        backButton = findViewById(R.id.backButton);

        if(wData == lData) {
            tieScenario();
        } else {
            fillText();
            while (count < 2) {
                setPics();
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CompareActivity.class));
            }
        });
        playSound(winner);
    }

    public void tieScenario() {
        winnerText.setText("It's a tie!");
        winnerData.setText("Both have " + comparable + " of: " + wData);
        loserData.setText("");
        setPics();
    }

    public void fillText() {
        winnerText.setText("Winner: " + winner);
        winnerData.setText("With " + comparable + " of: " + wData);
        loserData.setText("Loser: " + loser + " With " + comparable + " of: " + lData);
    }

    public void setPics() {
        setPic(winnerPic, winner);
        setPic(loserPic, loser);
    }

    public void setPic(ImageView profilePicture, String character) {

        switch (character) {
            case "Banjo":
                profilePicture.setImageResource(R.drawable.banjo);
                break;

            case "Bayonetta":
                profilePicture.setImageResource(R.drawable.bayo);
                break;

            case "Bowser":
                profilePicture.setImageResource(R.drawable.bowser);
                break;

            case "Bowser jr":
                profilePicture.setImageResource(R.drawable.bjr);
                break;

            case "Byleth":
                profilePicture.setImageResource(R.drawable.byleth);
                break;

            case "Captain Falcon":
                profilePicture.setImageResource(R.drawable.falcon);
                break;

            case "Chrom":
                profilePicture.setImageResource(R.drawable.chrom);
                break;

            case "Cloud":
                profilePicture.setImageResource(R.drawable.cloud);
                break;

            case "Corrin":
                profilePicture.setImageResource(R.drawable.corrin);
                break;

            case "Daisy":
                profilePicture.setImageResource(R.drawable.daisy);
                break;

            case "Dark Pit":
                profilePicture.setImageResource(R.drawable.dpit);
                break;

            case "Dark Samus":
                profilePicture.setImageResource(R.drawable.dsamus);
                break;

            case "Diddy Kong":
                profilePicture.setImageResource(R.drawable.diddy);
                break;

            case "Doctor Mario":
                profilePicture.setImageResource(R.drawable.doc);
                break;

            case "Donkey Kong":
                profilePicture.setImageResource(R.drawable.dk);
                break;

            case "Duck Hunt":
                profilePicture.setImageResource(R.drawable.duckhunt);
                break;

            case "Falco":
                profilePicture.setImageResource(R.drawable.falco);
                break;

            case "Fox":
                profilePicture.setImageResource(R.drawable.fox);
                break;

            case "Game and Watch":
                profilePicture.setImageResource(R.drawable.gnw);
                break;

            case "Ganondorf":
                profilePicture.setImageResource(R.drawable.ganon);
                break;

            case "Greninja":
                profilePicture.setImageResource(R.drawable.greninja);
                break;

            case "Hero":
                profilePicture.setImageResource(R.drawable.hero);
                break;

            case "Ice Climbers":
                profilePicture.setImageResource(R.drawable.ics);
                break;

            case "Ike":
                profilePicture.setImageResource(R.drawable.ike);
                break;

            case "Incineroar":
                profilePicture.setImageResource(R.drawable.incineroar);
                break;

            case "Inkling":
                profilePicture.setImageResource(R.drawable.inkling);
                break;

            case "Isabelle":
                profilePicture.setImageResource(R.drawable.isabelle);
                break;

            case "Jigglypuff":
                profilePicture.setImageResource(R.drawable.jiggs);
                break;

            case "Joker":
                profilePicture.setImageResource(R.drawable.joker);
                break;

            case "Ken":
                profilePicture.setImageResource(R.drawable.ken);
                break;

            case "King Dedede":
                profilePicture.setImageResource(R.drawable.dedede);
                break;

            case "King K Rool":
                profilePicture.setImageResource(R.drawable.kkr);
                break;

            case "Kirby":
                profilePicture.setImageResource(R.drawable.kirby);
                break;

            case "Link":
                profilePicture.setImageResource(R.drawable.link);
                break;

            case "Little Mac":
                profilePicture.setImageResource(R.drawable.mac);
                break;

            case "Lucario":
                profilePicture.setImageResource(R.drawable.lucario);
                break;

            case "Lucas":
                profilePicture.setImageResource(R.drawable.lucas);
                break;

            case "Lucina":
                profilePicture.setImageResource(R.drawable.lucina);
                break;

            case "Luigi":
                profilePicture.setImageResource(R.drawable.luigi);
                break;

            case "Mario":
                profilePicture.setImageResource(R.drawable.mario);
                break;

            case "Marth":
                profilePicture.setImageResource(R.drawable.marth);
                break;

            case "Mega Man":
                profilePicture.setImageResource(R.drawable.mega);
                break;

            case "Meta Knight":
                profilePicture.setImageResource(R.drawable.mk);
                break;

            case "Mewtwo":
                profilePicture.setImageResource(R.drawable.mewtwo);
                break;

            case "Mii Brawler":
                profilePicture.setImageResource(R.drawable.brawler);
                break;

            case "Mii Gunner":
                profilePicture.setImageResource(R.drawable.gunner);
                break;

            case "Mii Swordfighter":
                profilePicture.setImageResource(R.drawable.swordfighter);
                break;

            case "Ness":
                profilePicture.setImageResource(R.drawable.ness);
                break;

            case "Olimar":
                profilePicture.setImageResource(R.drawable.olimar);
                break;

            case "Pac Man":
                profilePicture.setImageResource(R.drawable.pacman);
                break;

            case "Palutena":
                profilePicture.setImageResource(R.drawable.palutena);
                break;

            case "Peach":
                profilePicture.setImageResource(R.drawable.peach);
                break;

            case "Pichu":
                profilePicture.setImageResource(R.drawable.pichu);
                break;

            case "Pikachu":
                profilePicture.setImageResource(R.drawable.pikachu);
                break;

            case "Piranha Plant":
                profilePicture.setImageResource(R.drawable.piranhaplant);
                break;

            case "Pit":
                profilePicture.setImageResource(R.drawable.pit);
                break;

            case "Pokemon Trainer":
                profilePicture.setImageResource(R.drawable.trainer);
                break;

            case "Richter":
                profilePicture.setImageResource(R.drawable.richter);
                break;

            case "Ridley":
                profilePicture.setImageResource(R.drawable.ridley);
                break;

            case "ROB":
                profilePicture.setImageResource(R.drawable.rob);
                break;

            case "Robin":
                profilePicture.setImageResource(R.drawable.robin);
                break;

            case "Rosalina":
                profilePicture.setImageResource(R.drawable.rosa);
                break;

            case "Roy":
                profilePicture.setImageResource(R.drawable.roy);
                break;

            case "Ryu":
                profilePicture.setImageResource(R.drawable.ryu);
                break;

            case "Samus":
                profilePicture.setImageResource(R.drawable.samus);
                break;

            case "Sheik":
                profilePicture.setImageResource(R.drawable.sheik);
                break;

            case "Shulk":
                profilePicture.setImageResource(R.drawable.shulk);
                break;

            case "Simon":
                profilePicture.setImageResource(R.drawable.simon);
                break;

            case "Snake":
                profilePicture.setImageResource(R.drawable.snake);
                break;

            case "Sonic":
                profilePicture.setImageResource(R.drawable.sonic);
                break;

            case "Terry":
                profilePicture.setImageResource(R.drawable.terry);
                break;

            case "Toon Link":
                profilePicture.setImageResource(R.drawable.tink);
                break;

            case "Villager":
                profilePicture.setImageResource(R.drawable.villager);
                break;

            case "Wario":
                profilePicture.setImageResource(R.drawable.wario);
                break;

            case "Wii Fit Trainer":
                profilePicture.setImageResource(R.drawable.wft);
                break;

            case "Wolf":
                profilePicture.setImageResource(R.drawable.wolf);
                break;

            case "Yoshi":
                profilePicture.setImageResource(R.drawable.yoshi);
                break;

            case "Young Link":
                profilePicture.setImageResource(R.drawable.yink);
                break;

            case "Zelda":
                profilePicture.setImageResource(R.drawable.zelda);
                break;

            case "Zero Suit Samus":
                profilePicture.setImageResource(R.drawable.zss);
                break;
        }
        count++;
    }

    public void playSound(String character) {

        switch (character) {
            case "Banjo":
                break;

            case "Bayonetta":
                break;

            case "Bowser":
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
                break;

            case "Mario":
                ring = MediaPlayer.create(CompareResultActivity.this, R.raw.mario);
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
                break;

            case "Peach":
                break;

            case "Pichu":
                break;

            case "Pikachu":
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
