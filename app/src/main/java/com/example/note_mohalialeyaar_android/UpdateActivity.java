package com.example.note_mohalialeyaar_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private EditText title, description;
    ImageView imageView;
    TextView address;
    Spinner spinner;
    int noteID ;
    DatabaseHelperClass helper ;
    NotesModelClass model  ;
    ArrayList<FolderModelClass> foldernames;

    int folderNameClicked;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    title = findViewById(R.id.update_title);
    description = findViewById(R.id.update_description);
    btnUpdate = findViewById(R.id.update);
    imageView = findViewById(R.id.updateShowImage);
    address = findViewById(R.id.address);
    spinner = findViewById(R.id.spinner);


    model = new NotesModelClass();
    helper = new DatabaseHelperClass(this);
    foldernames = helper.getFolderName();

    String[] adapterList = new String[foldernames.size()];
    for (int i = 0 ; i < foldernames.size(); i++){
        adapterList[i] = foldernames.get(i).getFolderName();
    }
    int[] idList = new int[foldernames.size()];
        for (int i = 0 ; i < foldernames.size(); i++){
            idList[i] = foldernames.get(i).getFolderId();
        }

     noteID = getIntent().getIntExtra("noteid", 1);



     model = helper.getNoteDetail(noteID);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,adapterList);
        spinner.setAdapter(adapter);




    try {
    if (model != null){
        title.setText(model.getNoteTitle());
        description.setText(model.getNoteDescription());
        address.setText(model.getNoteAddress());
        byte[] img = model.getNoteImage();

        Bitmap imageBitmapFromDB = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(imageBitmapFromDB);

    }
    else {
        Toast.makeText(this, "note not found", Toast.LENGTH_SHORT).show();
    }
    }catch(Exception e){
    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date(); // This object contains the current date value
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String realDate = formatter.format(date);

                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);


                byte [] imageInByte = byteArrayOutputStream.toByteArray();

folderNameClicked = spinner.getSelectedItemPosition();



                long result = helper.insertNotes(title.getText().toString(),
                        description.getText().toString(),
                        realDate,
                        idList[folderNameClicked],imageInByte,model.getNoteLocation(),model.getNoteAddress());

                if (result != -1 ){
                    helper.deleteNote(noteID);
                    Toast.makeText(UpdateActivity.this, "updated successfully ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                    else {
                    Toast.makeText(UpdateActivity.this, "Can not Update", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case(R.id.updateImageBtn):
                Toast.makeText(this,"image button clicked", Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,REQUEST_CODE);
            }
            else {
                Toast.makeText(this, "Do not  have permission to gallery", Toast.LENGTH_SHORT).show();
            }
            return;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{

            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){

                Uri filePath = data.getData();
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                imageView.setImageBitmap(imageBitmap);
            }

        }catch(Exception e){
            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}