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

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.NoteHolder> {

    private Context context;
    private List<String> noteList;
    private int count = 0;

    public AdapterNotes(Context context, List<String> noteList) {
        this.context = context;
        this.noteList = noteList;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_note, parent, false);
        NoteHolder noteHolder = new NoteHolder(view);
        return noteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        if(noteList.get(count) != null) {
            String used = noteList.get(count);
            holder.c_u.append(" " + used);
            count++;
            String fought = noteList.get(count);
            holder.o_u.append(" " + fought);
            count++;
            String result = noteList.get(count);
            if(result == "Win") {
                holder.picture.setImageResource(R.drawable.win);
                count++;
            } else if(result == "Lose") {
                holder.picture.setImageResource(R.drawable.lose);
                count++;
            }
            String text = noteList.get(count);
            holder.noteText.setText(text);
            int newCount = count + 1;
            if(noteList.get(newCount) != null) {
                count++;
            } else {
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
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


    }
}
