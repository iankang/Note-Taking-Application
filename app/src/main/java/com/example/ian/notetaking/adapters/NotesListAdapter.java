package com.example.ian.notetaking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ian.notetaking.R;
import com.example.ian.notetaking.models.Note;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteItemViewHolder>{

    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteListener onNoteListener;

    public NotesListAdapter(ArrayList<Note> notes, OnNoteListener onNoteListener) {
        this.notes = notes;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_list_item,viewGroup,false);

        return new NoteItemViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder noteItemViewHolder, int i) {

        noteItemViewHolder.noteTitle.setText(notes.get(i).getTitle());
        noteItemViewHolder.noteTimestamp.setText(notes.get(i).getTimestamp());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView noteTitle, noteTimestamp;
        OnNoteListener onNoteListener;

        public NoteItemViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteTimestamp = itemView.findViewById(R.id.note_timestamp);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClicked(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClicked(int position);
    }
}
