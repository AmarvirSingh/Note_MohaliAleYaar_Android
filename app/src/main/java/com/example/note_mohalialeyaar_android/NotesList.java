package com.example.note_mohalialeyaar_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mohalialeyaar_android.Adapters.NotesAdapter;
import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesList extends AppCompatActivity {
    RecyclerView listView;
    NotesAdapter notesAdapter;
    FloatingActionButton btnAdd;
    ArrayList<NotesModelClass> notes;
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    int PERMISSION_ID = 44;

    int folderID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retireiving folder id here;
        folderID = getIntent().getIntExtra("folderId", 1);




        setContentView(R.layout.activity_notes_list);
        listView = findViewById(R.id.listViewNotes);
        DatabaseHelperClass helper = new DatabaseHelperClass(this);
        btnAdd = findViewById(R.id.addBtnNotes);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // populating the array list using the method in  database helper class
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(NotesList.this, AddNoteActivity.class);
            intent.putExtra("folderId", folderID);
            startActivity(intent);

        });
        try {
            notes = helper.getAllNotes(folderID);

            if (notes.size() > 0) {
                // setting up folder adapter passing arguments to the contructor of folder adapter
                notesAdapter = new NotesAdapter(NotesList.this, notes, helper);
                int totalFolder = notes.size();
                listView.setLayoutManager(new LinearLayoutManager(this));

                listView.setAdapter(notesAdapter);
            }
        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        listView = findViewById(R.id.listViewNotes);
        DatabaseHelperClass helper = new DatabaseHelperClass(this);
        btnAdd = findViewById(R.id.addBtnNotes);
        // populating the array list using the method in  database helper class
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(NotesList.this,AddNoteActivity.class);
            intent.putExtra("folderId",folderID);
            startActivity(intent);

        });
        try{

            notes = helper.getAllNotes(folderID);

            Log.i("TAG", "onRestart: size of the notes array " + notes.size());
            if (notes.size() > 0) {
                // setting up folder adapter passing arguments to the contructor of folder adapter
                notesAdapter = new NotesAdapter(NotesList.this, notes, helper);
                int  totalFolder = notes.size();
                listView.setLayoutManager(new LinearLayoutManager(this));
                listView.setAdapter(notesAdapter);
            }
        }catch (Exception e){
            Toast.makeText(this, "notice = "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.note_list_menu,menu);
        MenuItem item = menu.findItem(R.id.search_bar_id_notes);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                notesAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notesAdapter.getFilter().filter(newText);
                return false;
            }
        });





        return super.onCreateOptionsMenu(menu);
    }





}
