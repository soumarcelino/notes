package com.m.notas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.m.notas.utils.Text;

public class NoteView extends AppCompatActivity {
    private String title;
    private String body;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Fade());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_note_view);

        TextView noteTitle = findViewById(R.id.noteViewTitle);
        TextView noteBody = findViewById(R.id.noteTitleViewBody);
        ImageView noteViewGravatar = findViewById(R.id.noteViewGravatar);

        Bundle extras = getIntent().getExtras();
        if( extras != null){
            title = extras.getString("title");
            body = extras.getString("body");
            id = extras.getInt("id");
            noteTitle.setText(title);
            noteBody.setText(body);
            String hash = Text.MD5(title.toLowerCase());
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
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("id", id);
        startActivityForResult(intent, 205, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void deleteNote(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", id);
        setResult(204, intent);
        finishAfterTransition();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 205){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", data.getIntExtra("id", 0));
            intent.putExtra("title", data.getStringExtra("title"));
            intent.putExtra("body", data.getStringExtra("body"));
            setResult(205, intent);
            finishAfterTransition();
        }
    }
}
