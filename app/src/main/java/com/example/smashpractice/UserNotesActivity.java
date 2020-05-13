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
    EditText newNoteText;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    AdapterNotes adapter;
    List<String> noteList;

    Button clearButton;

    UserInfo user;

    String email;
    String TAG = "NotesActivity";

    boolean notesFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        user = (UserInfo) getApplication();
        email = user.getEmail();
        noteList = new ArrayList<String>();
        noteList = getNotes(email);
        backButton = findViewById(R.id.backToProfile);
        newNoteText = findViewById(R.id.addNoteText);
        addButton = findViewById(R.id.addNoteButton);
        clearButton = findViewById(R.id.clearNotes);

        recyclerView = findViewById(R.id.myNotes);

        manager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(manager);


        adapter = new AdapterNotes(getBaseContext(), noteList);
        recyclerView.setAdapter(adapter);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }


    public List<String> getNotes(String email) {

        List<String> notesList = new ArrayList<String>();

        final RemoteMongoCollection<Document> collection =
                mongoClient.getDatabase("UserData").getCollection("Notes");

        // Only get journal entries from current user who is logged in
        Document filterDoc = new Document()
                .append("User_email", email);
        // Get all entries with the criteria from filterDoc
        RemoteFindIterable results = collection.find(filterDoc);

        // Log all journal entries that are found in the logger
        Log.d("Notes", String.valueOf(results));

        results.forEach(new Block() {
                            @Override
                            public void apply(Object item) {
                                Document doc = (Document) item;
                                String used = (String) doc.get("Char_used");
                                String fought = (String) doc.get("Char_fought");
                                String res = (String) doc.get("Result");
                                String text = (String) doc.get("Note");
                                notesList.add(used);
                                notesList.add(fought);
                                notesList.add(res);
                                notesList.add(text);
                            }
                        }
        );
        return notesList;
    }

}
