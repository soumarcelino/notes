package com.m.room_db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.m.notas.AppDatabase;
import com.m.notas.models.Note;
import com.m.notas.models.NoteDao;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAll();
    }

    public LiveData<List<Note>> getAllNotes(){
        return  allNotes;
    }

    public void insert(Note note){
        new insertAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new deleteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new updateAsyncTask(noteDao).execute(note);
    }


    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncTaskDao;

        insertAsyncTask(NoteDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncTaskDao;

        deleteAsyncTask(NoteDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncTaskDao;

        updateAsyncTask(NoteDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncTaskDao.update(params[0]);
            return null;
        }
    }

}
