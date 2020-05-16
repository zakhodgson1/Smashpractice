package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

import java.security.Key;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static com.example.smashpractice.DatabaseHelper.client;
import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class SurveyActivity extends AppCompatActivity {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    String main;

    Spinner mainSpinner;
    Button enterButton;

    //Strings passed from signup
    String gTag;
    String email;
    String password;

    TextView mainS;
    TextView tellUs;

    ImageView charPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Intent intent = getIntent();
        gTag = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        charPic = findViewById(R.id.charPic);
        charPic.setVisibility(View.INVISIBLE);

        mainSpinner = findViewById(R.id.mainSpinner);
        enterButton = findViewById(R.id.enterButton);
        mainS = findViewById(R.id.mainS);
        tellUs = findViewById(R.id.tellUs);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SurveyActivity.this,
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
    }

    public void goBackToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        Toast.makeText(this.getBaseContext(),"Please verify the email " + email, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void enterData() {
        main = mainSpinner.getSelectedItem().toString();
        insertUser(gTag, email, password, main);
    }

    public void insertUser(String g_tag, String e_mail, String pw, String main) {
        try {
            pw = encrypt(pw);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");


        Document doc = new Document()
                .append("GamerTag", g_tag)
                .append("Email", e_mail)
                .append("Password", pw)
                .append("Main", main);

        final Task<RemoteInsertOneResult> insert = coll.insertOne(doc);

        insert.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<RemoteInsertOneResult> task) {
                if (task.isSuccessful()) {
                    Log.i("STITCH", String.format("success inserting: %s",
                            task.getResult().getInsertedId()));
                    goBackToLogin();
                } else {
                    Log.e("app", "Failed to insert Document", task.getException());
                }
            }
        });
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

    public static String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(SurveyActivity.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;
    }
    private static Key generateKey() throws Exception
    {
        Key key = new SecretKeySpec(SurveyActivity.KEY.getBytes(),SurveyActivity.ALGORITHM);
        return key;
    }
}
