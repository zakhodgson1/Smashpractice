package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import static com.example.smashpractice.DatabaseHelper.mongoClient;


public class YTPlayerActivity extends AppCompatActivity {

    private YouTubePlayerView ytPlayer;
    private TextView videoTitle;
    UserInfo user;
    String email;
    Button addNoteButton;
    Button returnButton;
    EditText newNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ytplayer);

        user = (UserInfo) getApplication();
        email = user.getEmail();

        addNoteButton = findViewById(R.id.addNoteButton);
        newNoteText = findViewById(R.id.addNoteText);
        returnButton = findViewById(R.id.returnB);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.watch_nav);

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
//                        startActivity(new Intent(getApplicationContext()
//                                , WatchActivity.class));
                        //                       overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , WatchActivity.class));
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        ytPlayer = findViewById(R.id.yt_player);
        videoTitle = findViewById(R.id.video_title);

        Intent data = getIntent();
        final String videoId = data.getStringExtra("video_id");
        String video_title = data.getStringExtra("video_title");

        ytPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId,0);
            }
        });
        videoTitle.setText(video_title);
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
}
