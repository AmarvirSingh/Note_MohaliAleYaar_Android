package com.example.note_mohalialeyaar_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button btn;
    ArrayList<NotesModelClass> notesDesc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.title_note);
        description = findViewById(R.id.desc_note);
        btn = findViewById(R.id.btn_note);

    }
}