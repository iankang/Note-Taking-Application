package com.example.ian.notetaking.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ian.notetaking.models.Note;

import java.util.List;

@Dao
public interface NoteDAOInterface {

    @Insert
    long[] insertData(Note... notes);

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    @Delete
    long[] removeNote(Note... notes);

    @Update
    int[] updateNotes(Note... notes);
}
