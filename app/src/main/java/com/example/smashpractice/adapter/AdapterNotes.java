package com.example.smashpractice.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smashpractice.R;
import com.example.smashpractice.YTPlayerActivity;
import com.example.smashpractice.models.VideoYT;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdapterNotes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> noteList;

    public AdapterNotes(Context context, ArrayList<String> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView c_u, o_u, noteText;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.resPic);
            c_u = itemView.findViewById(R.id.usedBox);
            o_u = itemView.findViewById(R.id.oppBox);
            noteText = itemView.findViewById(R.id.noteTextList);
        }

        public void setData(ArrayList<String> data) {

            data = new ArrayList<String>();

            String getUsed = data.get(0);
            String getFought = data.get(1);
            String getResult = data.get(2);
            String getText = data.get(3);

            c_u.setText("You used: " + getUsed);
            o_u.setText("Opponent used: " + getFought);
            noteText.setText(getText);

            if (getResult == "Win") {

                Picasso.get()
                        .load(R.drawable.winPic)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(picture, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "Pic onSuccess method");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "Pic error: " + e);
                            }
                        });
            } else if (getResult == "Lose") {

                Picasso.get()
                        .load(R.drawable.losePic)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(picture, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "Pic onSuccess method");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "Pic error: " + e);
                            }
                        });
            }

        }

    }
}
