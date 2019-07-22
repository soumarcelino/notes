package com.m.notas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.m.notas.adapters.NoteAdapter;
import com.m.notas.models.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener {

    private ArrayList<Note> notes = new ArrayList<>();
    private NoteAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Fade());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewNotes = findViewById(R.id.RecyclerViewNotes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this, notes, this);
        recyclerViewNotes.setAdapter(noteAdapter);

        FloatingActionButton createNoteFab = findViewById(R.id.createNoteFab);
        createNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNote.class);
                startActivityForResult(intent,201, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

    }

    public void checkNotesIsEmptyAndUpdateAlert(){
        if(notes.size() > 0){
            shouldShowEmptyNote(false);
        } else {
            shouldShowEmptyNote(true);
        }
    }

    public void createNote(String title, String body){
        Note note = new Note(title, body);
        notes.add(note);
        noteAdapter.notifyDataSetChanged();
        checkNotesIsEmptyAndUpdateAlert();
    }

    public void updateNote(Note note, int id){
        notes.set(id, note);
        noteAdapter.notifyDataSetChanged();
    }

    public void removeNote(int id){
        notes.remove(id);
        noteAdapter.notifyDataSetChanged();
        checkNotesIsEmptyAndUpdateAlert();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("New param "+requestCode+" - "+resultCode);
        if(resultCode == 201){
            String title = data.getStringExtra("title");
            String body = data.getStringExtra("body");
            createNote(title, body);
        } else if(resultCode == 204){
            if(data.hasExtra("id")){
                int id = data.getIntExtra("id", 0);
                removeNote(id);
            }
        } else if(resultCode == 205){
            String title = data.getStringExtra("title");
            String body = data.getStringExtra("body");
            int id = data.getIntExtra("id",0);
            Note note = new Note(title, body);
            updateNote(note, id);
        }
    }

    private void shouldShowEmptyNote(Boolean shouldShowEmptyNote){
        TextView emptyNoteText = findViewById(R.id.emptyNoteText);
        ImageView emptyNoteImage = findViewById(R.id.emptyNoteImage);
        if(shouldShowEmptyNote){
            emptyNoteImage.setVisibility(View.VISIBLE);
            emptyNoteText.setVisibility(View.VISIBLE);
        } else {
            emptyNoteImage.setVisibility(View.GONE);
            emptyNoteText.setVisibility(View.GONE);
        }
    }

    public void openNoteView(Note note, int id){
        Intent intent = new Intent(this, NoteView.class);
        intent.putExtra("title", note.getName());
        intent.putExtra("body", note.getDescription());
        intent.putExtra("id", id);
        startActivityForResult(intent,204, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteView.class);
        Note note = notes.get(position);
        openNoteView(note, position);
    }
}
