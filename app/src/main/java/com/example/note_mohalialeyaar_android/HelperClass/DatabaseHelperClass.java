package com.example.note_mohalialeyaar_android.HelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.note_mohalialeyaar_android.FolderModelClass;
import com.example.note_mohalialeyaar_android.NotesModelClass;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelperClass extends SQLiteOpenHelper {


    private final Context context;
    private static final String DATABASE_NAME = "NotesDatabase";
    public static final int VERSION = 1;
    public static final String NOTE_TABLE = "note_table";
    public static final String FOLDER_TABLE = "folder_table";
    public static final String COLUMN_ID = "note_id";
    public static final String COLUMN_DATE = "note_date_time";
    public static final String COLUMN_TITLE = "note_title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "note_image";
    public static final String COLUMN_LOCATION = "note_location";
    public static final String COLUMN_ADDRESS = "note_address";
    public static final String COLUMN_FOLDER_NAME = "folder_name";
    public static final String COLUMN_FOLDER_ID = "folder_id";


    public DatabaseHelperClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + NOTE_TABLE + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT note_pk PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_TITLE + " TEXT NOT NULL , " +
                COLUMN_DESCRIPTION + " TEXT ," +
                COLUMN_DATE + " TEXT ," +
                COLUMN_IMAGE + " BLOB ," +
                COLUMN_LOCATION + " TEXT ," +
                COLUMN_ADDRESS + " TEXT ," +
                COLUMN_FOLDER_ID + " INTEGER"+ ")";

        String foldertableQuery = "CREATE TABLE IF NOT EXISTS " +FOLDER_TABLE+ "("+
                COLUMN_FOLDER_ID + " INTEGER NOT NULL CONSTRAINT folder_pk PRIMARY KEY AUTOINCREMENT, "+COLUMN_FOLDER_NAME+ " TEXT NOT NULL UNIQUE );";

        db.execSQL(foldertableQuery);

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS "+FOLDER_TABLE+ ";");

    }


    // insert folder function

    public long insertFolder (String folderName)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FOLDER_NAME,folderName);


        long result  = db.insert(FOLDER_TABLE,null,contentValues);

        return result;
    }

    public long insertNotes (String title, String description, String date, int folderId, byte[] imageinByte, String latLng, String location){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMAGE,imageinByte);
        cv.put(COLUMN_LOCATION,latLng);
        cv.put(COLUMN_ADDRESS, location);
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_FOLDER_ID,folderId);

        long result = db.insert(NOTE_TABLE,null,cv);

        return result;



    }


    public ArrayList<FolderModelClass> getFolderName()
    {

        try {
            SQLiteDatabase database = getReadableDatabase();
            Cursor cursor = null;
            cursor = database.rawQuery("SELECT * FROM " + FOLDER_TABLE + "", null);

            ArrayList<FolderModelClass> arrayList = new ArrayList<>();
            if (cursor.getCount() != 0) {

                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);

                    FolderModelClass modelClass = new FolderModelClass(id,name);

                    arrayList.add(modelClass);

                }

                cursor.close();

                return arrayList;

            }else {
                return  null;


            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    public ArrayList<NotesModelClass> getAllNotes(int folderId){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<NotesModelClass> list =  new ArrayList<>();

        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE + " WHERE "+ COLUMN_FOLDER_ID +"  = "+ String.valueOf(folderId), null);
        if (cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String location = cursor.getString(5);
                String address = cursor.getString(6);

                String date = cursor.getString(3);
                int folderID = cursor.getInt(7);

                NotesModelClass model = new NotesModelClass(id,title,description,date,folderID);
                list.add(model);

            }
        }
      cursor.close();
        return list;


    }




    public long deleteFolder(int folderId) {
        SQLiteDatabase db = getWritableDatabase();

        long result = db.delete(FOLDER_TABLE,"folder_id = ?",new String[]{String.valueOf(folderId)});

        return result;
    }


    public void  deleteNotesInfolder(int folderId){
        SQLiteDatabase db = getWritableDatabase();
        
        long result = db.delete(NOTE_TABLE,COLUMN_FOLDER_ID + " = ?",new String[]{String.valueOf(folderId)});
        
        if (result != -1){
            Toast.makeText(context, "Delete all the respective notes ", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "not deleting related notes", Toast.LENGTH_SHORT).show();
        }
        
    }

    // method to get number of notes present in the Folder
    public int getNumberOfNotes(int folderId){
        SQLiteDatabase db = getReadableDatabase();
        //int total = db.execSQL("SELECT COUNT(*) FROM " + NOTE_TABLE + " WHERE " + COLUMN_FOLDER_ID + " = " + folderId + " ; ");

        String query = "SELECT "+ COLUMN_TITLE +" FROM "+ NOTE_TABLE + " WHERE " + COLUMN_FOLDER_ID + " = " +folderId+ ";";
        ArrayList<String> names =  new ArrayList<>();


        Cursor cursor;
        cursor = db.rawQuery(query,null);
        if (cursor.getCount() != 0){
            while(cursor.moveToNext()){
                names.add(cursor.getString(0));

            }

        }
        Log.i("TAG", "getNumberOfNotes: id - "+folderId+ " size " + names.size());
        cursor.close();
        return names.size();
    }


    public NotesModelClass getNoteDetail(int noteID) {
        SQLiteDatabase db = getReadableDatabase();

        NotesModelClass model = new NotesModelClass();


        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM "+ NOTE_TABLE + " WHERE " + COLUMN_ID + " = "+ noteID, null);
        if(cursor!= null){
            while(cursor.moveToNext()){
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                byte[] imageByte = cursor.getBlob(4);
                String add = cursor.getString(6);

                model = new NotesModelClass(title, desc, imageByte,add);

            }
        }
        return  model;





    }

    public void updateNote(int noteID, String toString, String toString1) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,toString );
        cv.put(COLUMN_DESCRIPTION, toString1);

        long result = db.update(NOTE_TABLE,cv,COLUMN_ID+" = ? ",new String[]{String.valueOf(noteID)});
        if (result != -1){
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "not Updated successfully", Toast.LENGTH_SHORT).show();
        }



    }
}
