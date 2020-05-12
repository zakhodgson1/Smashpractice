package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class UserNotesActivity extends AppCompatActivity {

    Button backButton;
    Button addButton;
    EditText newNoteText;
    ListView userNotes;
    Button clearButton;

    UserInfo user;

    String email;
    String TAG = "NotesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        backButton = findViewById(R.id.backToProfile);
        newNoteText = findViewById(R.id.addNoteText);
        addButton = findViewById(R.id.addNoteButton);
        userNotes = findViewById(R.id.myNotes);
        clearButton = findViewById(R.id.clearNotes);

        user = (UserInfo) getApplication();
        email = user.getEmail();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }
}
