package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.smashpractice.adapter.AdapterNotes;
import com.example.smashpractice.models.Note;
import com.mongodb.Block;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class UserNotesActivity extends AppCompatActivity {

    Button backButton;
    Button addButton;
    Button notesButton;
    EditText newNoteText;
    RecyclerView recyclerView;
    LinearLayoutManager manager = new LinearLayoutManager(UserNotesActivity.this);
    AdapterNotes adapter;
    public List<Note> noteList = new ArrayList<>();

    Button clearButton;

    UserInfo user;

    String email;
    String TAG = "NotesActivity";

    boolean pulledNotes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        user = (UserInfo) getApplication();
        email = user.getEmail();
        noteList = getNotes(email);

        backButton = findViewById(R.id.backToProfile);
        notesButton = findViewById(R.id.notesButton);
        newNoteText = findViewById(R.id.addNoteText);
        addButton = findViewById(R.id.addNoteButton);
        clearButton = findViewById(R.id.clearNotes);
        recyclerView = findViewById(R.id.myNotes);

        if(pulledNotes == false) {
            while(pulledNotes == false) {

            }
        } else {
            recyclerView.setLayoutManager(manager);
            adapter = new AdapterNotes(UserNotesActivity.this, noteList);
            recyclerView.setAdapter(adapter);
            pulledNotes = true;
        }

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteList = getNotes(email);
                adapter.notifyDataSetChanged();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }


    public List<Note> getNotes(String email) {

        List<Note> notesList = new ArrayList<>();

        final RemoteMongoCollection<Document> collection =
                mongoClient.getDatabase("UserData").getCollection("Notes");

        // Only get journal entries from current user who is logged in
        Document filterDoc = new Document()
                .append("User_email", email);
        // Get all entries with the criteria from filterDoc
        RemoteFindIterable results = collection.find(filterDoc);


        results.forEach(new Block() {
                            @Override
                            public void apply(Object item) {
                                Document doc = (Document) item;
                                Note nextNote = new Note();
                                String used = (String) doc.get("Char_used");
                                nextNote.setUsed(used);
                                String fought = (String) doc.get("Char_fought");
                                nextNote.setFought(fought);
                                String res = (String) doc.get("Result");
                                nextNote.setResult(res);
                                String text = (String) doc.get("Note");
                                nextNote.setText(text);
                                Log.d("Notes", used + fought + res + text);
                                notesList.add(nextNote);
                            }
                        }
        );
        Log.d("Notes", "");
        pulledNotes = true;
        return notesList;
    }
}
