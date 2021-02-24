package com.example.note_mohalialeyaar_android;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mohalialeyaar_android.Adapters.NotesAdapter;
import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesList extends AppCompatActivity {
    RecyclerView listView;
    NotesAdapter notesAdapter;
    FloatingActionButton btnAdd;
    ArrayList<NotesModelClass> notes;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_notes_list);
        listView = findViewById(R.id.listViewNotes);
        DatabaseHelperClass helper = new DatabaseHelperClass(this);
        btnAdd = findViewById(R.id.addBtnNotes);
        // populating the array list using the method in  database helper class

        try{
            notes = helper.getNotesList();

            if (notes.size() > 0) {
                // setting up folder adapter passing arguments to the contructor of folder adapter
                notesAdapter = new NotesAdapter(NotesList.this, notes, helper);
                int  totalFolder = notes.size();
                listView.setLayoutManager(new LinearLayoutManager(this));

                listView.setAdapter(notesAdapter);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
