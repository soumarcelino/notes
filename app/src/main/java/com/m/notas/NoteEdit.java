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

import com.koushikdutta.ion.Ion;
import com.m.notas.models.Note;
import com.m.notas.models.NoteViewModel;
import com.m.notas.utils.Text;

public class NoteEdit extends AppCompatActivity {
    private Note note;
    private TextView noteTitle;
    private TextView noteBody;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_edit_note);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        Bundle extras = getIntent().getExtras();
        noteTitle = findViewById(R.id.editNoteTitle);
        noteBody = findViewById(R.id.editNoteBody);
        if( extras != null){
            note = (Note) extras.getSerializable("note");
            noteTitle.setText(note.getName());
            noteBody.setText(note.getDescription());
        }
        Button editConfirm = findViewById(R.id.editConfirmButton);
        editConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmEdit();
            }
        });
        Button cancelButton = findViewById(R.id.editCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });
    }
    private void confirmEdit(){
        note.setName(noteTitle.getText().toString());
        note.setDescription(noteBody.getText().toString());
        noteViewModel.update(note);
        finish();
    }
}
