package com.m.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.m.notas.utils.Text;

public class NoteEdit extends AppCompatActivity {
    private String title, body;
    private int id;
    private TextView noteTitle;
    private TextView noteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_edit_note);

        Bundle extras = getIntent().getExtras();
        noteTitle = findViewById(R.id.editNoteTitle);
        noteBody = findViewById(R.id.editNoteBody);
        if( extras != null){
            title = extras.getString("title");
            body = extras.getString("body");
            id = extras.getInt("id");
            noteTitle.setText(title);
            noteBody.setText(body);
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", noteTitle.getText().toString());
        intent.putExtra("body", noteBody.getText().toString());
        intent.putExtra("id", id);
        setResult(205, intent);
        finish();
    }
}
