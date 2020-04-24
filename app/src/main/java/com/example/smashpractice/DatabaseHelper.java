package com.example.smashpractice;

import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;

public class DatabaseHelper {

    // Connect to MongoDB client
    final static StitchAppClient client =
            Stitch.getAppClient("smashpractice-jxlen");

    final static RemoteMongoClient mongoClient =
            client.getServiceClient(RemoteMongoClient.factory, "SmashPractice");
}
