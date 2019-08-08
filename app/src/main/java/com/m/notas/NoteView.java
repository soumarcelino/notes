package com.m.notas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.m.notas.models.Note;
import com.m.notas.models.NoteViewModel;
import com.m.notas.utils.Text;

public class NoteView extends AppCompatActivity {
    private Note note;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Fade());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_note_view);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        TextView noteTitle = findViewById(R.id.noteViewTitle);
        TextView noteBody = findViewById(R.id.noteTitleViewBody);
        ImageView noteViewGravatar = findViewById(R.id.noteViewGravatar);

        Bundle extras = getIntent().getExtras();
        if( extras != null){
            note = (Note) extras.getSerializable("note");
            noteTitle.setText(note.getName());
            noteBody.setText(note.getDescription());
            String hash = Text.MD5(note.getName().toLowerCase());
            Ion.with(this)
                    .load("https://www.gravatar.com/monsterid/"+hash+"?s=500")
                    .withBitmap()
                    .intoImageView(noteViewGravatar);
        }

        Button noteViewDelete = findViewById(R.id.noteViewDelete);
        noteViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(NoteView.this)
                        .setTitle("Remoção de nota")
                        .setMessage("Tens certeza que deseja apagar a nota?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteNote();
                            }
                        }).setNegativeButton("NÃO", null).show();
            }
        });


        Button noteViewEdit = findViewById(R.id.noteViewEdit);
        noteViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
            }
        });
    }
    private void editNote(){
        Intent intent = new Intent(this, NoteEdit.class);
        intent.putExtra("note", note);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    private void deleteNote(){
        noteViewModel.delete(note);
        finishAfterTransition();

    }
}
