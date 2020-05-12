package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mongodb.Block;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class UserNotesActivity extends AppCompatActivity {

    Button backButton;
    Button addButton;
    EditText newNoteText;
    RecyclerView userNotes;
    Button clearButton;

    UserInfo user;

    String email;
    String TAG = "NotesActivity";

    ArrayList<String> userNotesList;
    ArrayAdapter<String> theIllestFuckenAdapterInThisCoffeeCup;

    boolean isAddVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        backButton = findViewById(R.id.backToProfile);
        newNoteText = findViewById(R.id.addNoteText);
        addButton = findViewById(R.id.addNoteButton);
        userNotes = findViewById(R.id.myNotes);
        clearButton = findViewById(R.id.clearNotes);

        userNotesList = new ArrayList<>();
        theIllestFuckenAdapterInThisCoffeeCup = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNotesList);

        getNotes();


        user = (UserInfo) getApplication();
        email = user.getEmail();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    public void getNotes() {

        final RemoteMongoCollection<Document> collection =
                mongoClient.getDatabase("UserData").getCollection("Notes");

        // Only get journal entries from current user who is logged in
        Document filterDoc = new Document()
                .append("user_email", email);

        // Get all entries with the criteria from filterDoc
        RemoteFindIterable results = collection.find(filterDoc);
        // Log all journal entries that are found in the logger
        Log.d("Notes", String.valueOf(results));

        results.forEach(new Block() {
            @Override
            public void apply(Object item) {
                Document doc = (Document) item;
                String c_u = (String) doc.get("Char_used");
                String o_u = (String) doc.get("Char_fought");
                String g_r = (String) doc.get("Result");
                String note_text = (String) doc.get("Note");

                userNotesList.add(c_u);
                userNotesList.add(o_u);
                userNotesList.add(g_r);
                userNotesList.add(note_text);
                theIllestFuckenAdapterInThisCoffeeCup.notifyDataSetChanged();
            }
        });
    }






}
