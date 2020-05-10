package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smashpractice.adapter.AdapterHome;
import com.example.smashpractice.models.ModelHome;
import com.example.smashpractice.models.VideoYT;
import com.example.smashpractice.network.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WatchActivity extends AppCompatActivity {

    private AdapterHome adapter;
    private LinearLayoutManager manager;
    private List<VideoYT> videoList = new ArrayList<>();

    private EditText input_query;
    private Button btn_search;
    private RecyclerView rv;

    public String TAG = "WatchActivity";

    private String API_KEY = "AIzaSyDCmrfsUUfTHcUmBwiPAF8RUx_H90ie2ZQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        input_query = findViewById(R.id.input_query);
        btn_search = findViewById(R.id.search_button);
        rv = findViewById(R.id.recycler_search);

        adapter = new AdapterHome(getBaseContext(), videoList);
        manager = new LinearLayoutManager(getBaseContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input_query.getText().toString())) {
                    getJson(input_query.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "You need to enter some text!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getJson(String query) {
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.mx + YoutubeAPI.ord
                + YoutubeAPI.part + YoutubeAPI.query + query + YoutubeAPI.type;
        Call<ModelHome> data = YoutubeAPI.getHomeVideo().getYT(url);
        data.enqueue(new Callback<ModelHome>() {

            @Override
            public void onResponse(Call<ModelHome> call, Response<ModelHome> response) {
                if(response.errorBody() != null) {
                    Log.d(TAG, "onResponse search: " + response.errorBody().toString());
                } else {
                    Log.d(TAG, "In the else statement");
                    ModelHome mh = response.body();
                    if(mh.getItems().size() != 0) {
                        videoList.addAll(mh.getItems());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getBaseContext(), "No videos found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelHome> call, Throwable t) {
                Log.e(TAG, "onFailure search: ", t);
            }
        });
    }




}
