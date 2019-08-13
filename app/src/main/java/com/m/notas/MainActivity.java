package com.m.notas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
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
import com.m.notas.models.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener {

    private ArrayList<Note> notes = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;
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

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));

        recyclerViewNotes.addItemDecoration(itemDecorator);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                updateNotes(notes);
            }
        });

        FloatingActionButton createNoteFab = findViewById(R.id.createNoteFab);
        createNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNote.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

    }

    public void updateNotes(List<Note> mNotes){
        notes.clear();
        notes.addAll(mNotes);
        noteAdapter.notifyDataSetChanged();
        checkNotesIsEmptyAndUpdateAlert();
    }

    public void checkNotesIsEmptyAndUpdateAlert(){
        if(notes.size() > 0){
            shouldShowEmptyNote(false);
        } else {
            shouldShowEmptyNote(true);
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

    public void openNoteView(Note note){
        Intent intent = new Intent(this, NoteView.class);
        intent.putExtra("note", note);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onNoteClick(int position) {
        Note note = notes.get(position);
        openNoteView(note);
    }
}
