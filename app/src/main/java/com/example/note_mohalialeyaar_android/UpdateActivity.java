package com.example.note_mohalialeyaar_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    private EditText title, description;
    ImageView imageView;
    TextView address;
    int noteID ;
    DatabaseHelperClass helper ;
    NotesModelClass model  ;

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

    model = new NotesModelClass();
    helper = new DatabaseHelperClass(this);

     noteID = getIntent().getIntExtra("noteid", 1);

    model = helper.getNoteDetail(noteID);
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
                helper.updateNote(noteID, title.getText().toString(),
                        description.getText().toString());
                finish();


            }
        });

    }
}