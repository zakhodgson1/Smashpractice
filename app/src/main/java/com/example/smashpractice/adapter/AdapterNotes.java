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
import com.example.smashpractice.models.Note;
import com.example.smashpractice.models.VideoYT;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.NoteHolder> {

    private Context context;
    private List<Note> data;

    public AdapterNotes(Context context, List<Note> data) {
        this.context = context;
        this.data = data;
        Log.d("Notes", "Constructor");
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Notes", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_note, parent, false);
        NoteHolder noteHolder = new NoteHolder(view);
        return noteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Log.d("Notes", "onBindViewHolder");
        Note userNote = data.get(position);
        NoteHolder noteHolder = (NoteHolder) holder;
        noteHolder.setData(userNote);
    }

    @Override
    public int getItemCount() {
        Log.d("Notes", data.toString());
        return data.size();
    }


    public static class NoteHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView c_u, o_u, noteText;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.resPic);
            c_u = itemView.findViewById(R.id.usedBox);
            o_u = itemView.findViewById(R.id.oppBox);
            noteText = itemView.findViewById(R.id.noteTextList);
        }

        public void setData(Note data) {
            Log.d("Notes", "setData");
            String getUsed = data.getUsed();
            String getFought = data.getFought();
            String getResult = data.getResult();
            String getText = data.getText();

            c_u.append(" " + getUsed);
            o_u.append(" " + getFought);
            if(getResult == "Win") {
                picture.setImageResource(R.drawable.win);
            } else if(getResult == "Lose") {
                picture.setImageResource(R.drawable.lose);
            }
            noteText.setText(getText);
        }
    }
}
