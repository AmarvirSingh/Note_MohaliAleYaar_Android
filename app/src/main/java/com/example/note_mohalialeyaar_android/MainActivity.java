package com.example.note_mohalialeyaar_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addBtn;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        addBtn = findViewById(R.id.addBtn);
        DatabaseHelperClass helper = new DatabaseHelperClass(getApplicationContext());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View view = layoutInflater.inflate(R.layout.activity_add_folder_actvity,null,false);
                builder.setView(view).create().show();

                EditText etFolderName = view.findViewById(R.id.folderName);
                Button btnAddFolder = view.findViewById(R.id.addFolderBtn);



               btnAddFolder.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       String folderName = etFolderName.getText().toString();

                       if (etFolderName.getText().toString().isEmpty())
                       {
                           etFolderName.setError("PLease add foldr name");
                           etFolderName.requestFocus();
                           return;
                       }


                       boolean bool = helper.insertFolder(folderName);
                       if (bool)
                           Toast.makeText(MainActivity.this, "Data Added successfully", Toast.LENGTH_SHORT).show();
                       else
                           Toast.makeText(MainActivity.this, "not added", Toast.LENGTH_SHORT).show();









                   }
               });







            }
        });


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
                Toast.makeText(this, "Show Folder Button Clicked ", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.search):
                Toast.makeText(this, "Search Button Clicked ", Toast.LENGTH_SHORT).show();
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}