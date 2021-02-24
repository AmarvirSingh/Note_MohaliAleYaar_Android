package com.example.note_mohalialeyaar_android.HelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.note_mohalialeyaar_android.FolderModelClass;
import com.example.note_mohalialeyaar_android.NotesModelClass;

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
    public static final String COLUMN_DESCRIPTION = "note_description";
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
                COLUMN_TITLE + " TEXT NOT NULL, " +
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

    public long insertFolder (String folderName)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FOLDER_NAME,folderName);


        long result  = db.insert(FOLDER_TABLE,null,contentValues);

        return result;
    }

    public long insertNote(String title,String Desc,String location,String image,String address)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE,title);
        contentValues.put(COLUMN_DESCRIPTION,Desc);
        Date date = new Date(); // This object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        contentValues.put(COLUMN_DATE,formatter.format(date));
        contentValues.put(COLUMN_LOCATION,location);
        contentValues.put(COLUMN_IMAGE,image);
        contentValues.put(COLUMN_ADDRESS,address);
        contentValues.put(COLUMN_FOLDER_ID,4);
        long result = db.insert(NOTE_TABLE,null,contentValues);
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

    public ArrayList<NotesModelClass> getNotesList()
    {

        try {
            SQLiteDatabase database = getReadableDatabase();
            Cursor cursor = null;
            cursor = database.rawQuery("SELECT * FROM " + NOTE_TABLE + "", null);

            ArrayList<NotesModelClass> arrayList = new ArrayList<>();
            if (cursor.getCount() != 0) {

                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String desc = cursor.getString(2);
                    String loc = cursor.getString(5);
                    byte[] s = title.getBytes();
                    NotesModelClass modelClass = new NotesModelClass(id,title,desc,"",loc,"",s);

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

    public long deleteFolder(int folderId) {
        SQLiteDatabase db = getWritableDatabase();

        long result = db.delete(FOLDER_TABLE,"folder_id = ?",new String[]{String.valueOf(folderId)});

        return result;
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


}
