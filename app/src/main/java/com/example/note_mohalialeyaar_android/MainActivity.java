package com.example.note_mohalialeyaar_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mohalialeyaar_android.Adapters.FolderAdapter;
import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addBtn;
    RecyclerView recyclerView;
    TextView total;
    ArrayList<FolderModelClass>  folderNames =  new ArrayList<>();
    private int totalFolder = 0;
    DatabaseHelperClass helper;
    FolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        addBtn = findViewById(R.id.addBtn);
        total = findViewById(R.id.total);

        helper = new DatabaseHelperClass(this);

        // populating the array list using the method in  database helper class

        try{
            folderNames = helper.getFolderName();

            if (folderNames.size() > 0) {
                // setting up folder adapter passing arguments to the contructor of folder adapter
                adapter = new FolderAdapter(MainActivity.this, folderNames, helper);
                totalFolder = folderNames.size();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                recyclerView.setAdapter(adapter);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }






        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View view = layoutInflater.inflate(R.layout.activity_add_folder_actvity,null,false);
                builder.setView(view);

                EditText etFolderName = view.findViewById(R.id.folderName);
                Button btnAddFolder = view.findViewById(R.id.addFolderBtn);





                btnAddFolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String folderName = etFolderName.getText().toString();

                        if (etFolderName.getText().toString().isEmpty()) {
                            etFolderName.setError("Please add folder name");
                            etFolderName.requestFocus();
                            return;
                        }
                        long result = helper.insertFolder(folderName);
                        if(result != -1 ){
                            Toast.makeText(MainActivity.this, "Data Added successfully", Toast.LENGTH_SHORT).show();

                            //********************************
//                           folderNames.clear();
                            try{
                                folderNames = helper.getFolderName();

                                if (folderNames.size() > 0) {
                                    // settoing up folder adapter passing arguments to the contructor of folder adapter
                                    adapter = new FolderAdapter(MainActivity.this, folderNames, helper);
                                    totalFolder = folderNames.size();
                                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                                    recyclerView.setAdapter(adapter);
                                }
                            }catch (Exception e){
//                               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                            total.setText(String.valueOf("Total Folders :" + totalFolder));

                            adapter.notifyDataSetChanged();

                        }else {
                            //     Toast.makeText(MainActivity.this, "Data not added ", Toast.LENGTH_SHORT).show();
                            etFolderName.setError("name Already talken");
                            etFolderName.requestFocus();
                            return;


                        }


                    }
                });
                AlertDialog alert =  builder.create();
                alert.show();
            }
        });


        total.setText(String.valueOf("Total Folders :" + totalFolder));

    }


    @Override
    protected void onRestart() {

        try{
            folderNames = helper.getFolderName();

            if (folderNames.size() > 0) {
                // setting up folder adapter passing arguments to the contructor of folder adapter
                adapter = new FolderAdapter(MainActivity.this, folderNames, helper);
                totalFolder = folderNames.size();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                recyclerView.setAdapter(adapter);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainactivity_menu,menu);
        MenuItem item = menu.findItem(R.id.search_bar_id);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });





        return super.onCreateOptionsMenu(menu);
    }

  }