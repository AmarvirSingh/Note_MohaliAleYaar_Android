package com.example.note_mohalialeyaar_android.HelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.note_mohalialeyaar_android.FolderModelClass;

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
                COLUMN_DESCRIPTION + "TEXT ," +
                COLUMN_DATE + " TEXT ," +
                COLUMN_IMAGE + " BLOB ," +
                COLUMN_LOCATION + " TEXT ," +
                COLUMN_ADDRESS + " TEXT ," +
                COLUMN_FOLDER_ID + " INTEGER ," +
                "FOREIGN KEY(" + COLUMN_FOLDER_ID + ") REFERENCES " + FOLDER_TABLE + "(" + COLUMN_FOLDER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
                " );";

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

    public boolean insertFolder (String folderName)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FOLDER_NAME,folderName);


       long result  = db.insert(FOLDER_TABLE,null,contentValues);
        if(result != -1 ){
            return true;
        }else {
            return false;
        }

    }

}
