package com.example.smashpractice;

import android.util.Log;

import com.mongodb.Block;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import org.bson.Document;

import java.util.List;

public class DatabaseHelper {

    // Connect to MongoDB client
    final static StitchAppClient client =
            Stitch.getAppClient("smashpractice-jxlen");

    final static RemoteMongoClient mongoClient =
            client.getServiceClient(RemoteMongoClient.factory, "SmashPractice");

}
