package com.m.notas.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM NOTES")
    LiveData<List<Note>> getAll();

    @Query("SELECT * FROM NOTES WHERE id IN (:notesId)")
    List<Note> loadAllByIds(int[] notesId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Note note);

    @Delete
    void delete(Note note);
}
