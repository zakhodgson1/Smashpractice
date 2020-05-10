package com.example.smashpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class WatchActivity extends AppCompatActivity {

    EditText input_query;
    Button btn_search;
    RecyclerView rv;

    private String API_KEY = "AIzaSyDCmrfsUUfTHcUmBwiPAF8RUx_H90ie2ZQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        input_query = findViewById(R.id.input_query);
        btn_search = findViewById(R.id.search_button);
        rv = findViewById(R.id.recycler_search);
    }
}
