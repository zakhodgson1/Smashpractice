package com.example.smashpractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.mongodb.client.model.Filters;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.smashpractice.DatabaseHelper.mongoClient;

public class ProfileActivity extends AppCompatActivity {

    String email;
    String userPath;
    String gamerTag;

    TextView header;
    TextView emailText;
    ImageView profilePicture;
    Button pictureB;
    Button logoutB;
    Button statsB;

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String[] permissionsNeeded = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            // requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            requestPermissions(permissionsNeeded, PERMISSION_REQUEST);
        }

        header = findViewById(R.id.header);
        header.setText("");
        emailText = findViewById(R.id.emailText);
        profilePicture = findViewById(R.id.profilePic);
        pictureB = findViewById(R.id.pictureB);
        logoutB = findViewById(R.id.logoutB);
        statsB = findViewById(R.id.statsB);

        UserInfo user = (UserInfo) getApplication();
        email = user.getEmail();
        gamerTag = user.getUserName();


        emailText.setText(email);
        getData();
        if(header.length() == 0) {
            getData();
        }

        pictureB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
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




        setProfilePic();
    }

    public void sendData(String username)
    {
        UserInfo user = (UserInfo) getApplication();
        user.setUserName(username);
    }

    public void getData() {
        RemoteFindIterable<Document> results;
        // Connect to MongoDB Client
        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");
        results = coll.find(Filters.eq("email", email))
                .projection(
                        new Document());
        results.forEach(item -> {
            try {
                JSONObject obj = new JSONObject(item.toJson());
                Log.d("RESULTS", obj.toString());
                gamerTag = obj.getString("GamerTag");
                header.setText(gamerTag + "'s Profile");
                sendData(gamerTag);
            } catch (JSONException e) {
                Log.d("JSON exception:", e.toString());
            }
        });
    }

    public void setProfilePic() {
        UserInfo user = (UserInfo) getApplication();
        String path = user.getUserPath();
        String uemail = user.getEmail();
        RemoteFindIterable<Document> results;


        final StitchAppClient client =
                Stitch.getAppClient("smashpractice-jxlen");
        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "SmashPractice");
        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("UserData").getCollection("Users");
        results = coll.find(Filters.eq("email", uemail))
                .projection(
                        new Document());
        results.forEach(item -> {
            try {
                JSONObject obj = new JSONObject(item.toJson());
                String up = obj.getString("path").toString();
                userPath = up;
            } catch (JSONException e) {
                Log.d("JSON exception:", e.toString());
            }
        });


        if(userPath != "")
        {
            profilePicture.setImageBitmap(BitmapFactory.decodeFile(userPath));
        }
        if(path != null)
        {
            profilePicture.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

                }
        }
    }

    @SuppressLint("MissingSuperCall")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        RemoteFindIterable<Document> results;
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    UserInfo user = (UserInfo) getApplication();
                    user.setUserPath(picturePath);

                    final StitchAppClient client =
                            Stitch.getAppClient("smashpractice-jxlen");
                    final RemoteMongoClient mongoClient =
                            client.getServiceClient(RemoteMongoClient.factory, "SmashPractice");
                    final RemoteMongoCollection<Document> coll =
                            mongoClient.getDatabase("UserData").getCollection("Users");

                    String email = user.getEmail();
                    Document query = new Document().append("email", email);

                    Document update = new Document().append("$push", new Document().append("path", picturePath.toString()));

                    final Task<RemoteUpdateResult> updateTask = coll.updateOne(query, update);
                    profilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
    }
}
