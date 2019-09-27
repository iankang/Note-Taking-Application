package com.example.ian.notetaking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ian.notetaking.adapters.NotesListAdapter;
import com.example.ian.notetaking.models.Note;
import com.example.ian.notetaking.utils.NoteItemDecorator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotesListAdapter.OnNoteListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    public RecyclerView recyclerView;
    public NotesListAdapter notesListAdapter;


    public ArrayList<Note> mnotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);

        initRecyclerView();
        fakeData();

        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Nota");
    }


    public void fakeData(){
        Log.e(TAG, "fakeData: ");
        for(int i = 0; i<1000; i++){
            Note note = new Note();
            note.setContent("content # :" + i);
            note.setTitle("title # :" + i);
            note.setTimestamp("Jan 2019 :" + i);
            mnotes.add(note);
        }
        notesListAdapter.notifyDataSetChanged();
    }

    public void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: sida");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        notesListAdapter = new NotesListAdapter(mnotes, this);
        recyclerView.setAdapter(notesListAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
        NoteItemDecorator itemDecorator = new NoteItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);


    }

    @Override
    public void onNoteClicked(int position) {

        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mnotes.get(position));
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }

    private void removeNote(Note note){
        mnotes.remove(note);
        notesListAdapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            removeNote(mnotes.remove(viewHolder.getAdapterPosition()));
        }
    };
}
