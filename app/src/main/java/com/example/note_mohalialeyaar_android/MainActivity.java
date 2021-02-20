package com.example.note_mohalialeyaar_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.mainactivity_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case(R.id.deleteBtn):
                Toast.makeText(this,"Delete button clicked", Toast.LENGTH_SHORT).show();
                break;
            case(R.id.folderBtn):
                Toast.makeText(this, "Show Folder Butoon Clicked ", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.search):
                Toast.makeText(this, "Search Button Clicked ", Toast.LENGTH_SHORT).show();
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}