package com.m.notas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.m.notas.models.Note;
import com.m.notas.models.NoteViewModel;

public class CreateNote extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Fade());
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_create_note);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        Button create = findViewById(R.id.editConfirmButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView title = findViewById(R.id.editNoteTitle);
                TextView body = findViewById(R.id.editNoteBody);
                note = new Note(title.getText().toString(), body.getText().toString());
                noteViewModel.insert(note);
                finishAfterTransition();
            }
        });

        Button cancel = findViewById(R.id.editCancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });
    }
}
