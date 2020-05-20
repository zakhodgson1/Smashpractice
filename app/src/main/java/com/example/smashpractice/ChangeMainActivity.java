package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.client.model.Filters;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class ChangeMainActivity extends AppCompatActivity {

    Spinner mainSpinner;
    Button enterButton;
    Button backButton;
    String email;
    String main;
    ImageView charPic;

    MediaPlayer ring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_main);

        UserInfo user = (UserInfo) getApplication();
        email = user.getEmail();

        charPic = findViewById(R.id.charPic);
        charPic.setVisibility(View.INVISIBLE);

        mainSpinner = findViewById(R.id.mainSpinner);
        enterButton = findViewById(R.id.enterButton);
        backButton = findViewById(R.id.backButton);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ChangeMainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.charNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(myAdapter);

        mainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String character = mainSpinner.getSelectedItem().toString();
                showPic(character);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enterData();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goBack();
            }
        });

    }

    public void enterData() {
        main = mainSpinner.getSelectedItem().toString();

        Document filterDoc = new Document().append("Email", email);
        Document updateDoc = new Document().append("$set",
            new Document()
                .append("Main", main));

        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");

        final Task<RemoteUpdateResult> updateTask =
                coll.updateOne(filterDoc, updateDoc);
        updateTask.addOnCompleteListener(new OnCompleteListener<RemoteUpdateResult>() {
            @Override
            public void onComplete(@NonNull Task <RemoteUpdateResult> task) {
                if (task.isSuccessful()) {

                    Log.d("app", String.format("successfully modified document"));
                } else {
                    Log.e("app", "failed to update document with: ", task.getException());
                }
            }
        });
        goBack();
    }

    public void goBack() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void showPic(String character) {
        switch (character) {
            case "Banjo":
                charPic.setImageResource(R.drawable.banjo);
                break;

            case "Bayonetta":
                charPic.setImageResource(R.drawable.bayo);
                break;

            case "Bowser":
                charPic.setImageResource(R.drawable.bowser);
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.bowser);
                ring.start();
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
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.donkey);
                ring.start();
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
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.ganondorf);
                ring.start();
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
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.luigi);
                ring.start();
                break;

            case "Mario":
                charPic.setImageResource(R.drawable.mario);
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.mario);
                ring.start();
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
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.palu);
                ring.start();
                break;

            case "Peach":
                charPic.setImageResource(R.drawable.peach);
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.peach);
                ring.start();
                break;

            case "Pichu":
                charPic.setImageResource(R.drawable.pichu);
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.pichu);
                ring.start();
                break;

            case "Pikachu":
                charPic.setImageResource(R.drawable.pikachu);
                ring = MediaPlayer.create(ChangeMainActivity.this, R.raw.pikachu);
                ring.start();
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

}
