package com.example.note_mohalialeyaar_android.HelperClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperClass extends SQLiteOpenHelper {


    private Context context;
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
                COLUMN_ID + "INTEGER NOT NULL ," +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + "TEXT NOT NULL," +
                COLUMN_DATE + " TEXT NOT NULL," +
                COLUMN_IMAGE + "BLOB NOT NULL," +
                COLUMN_LOCATION + "TEXT NOT NULL," +
                COLUMN_ADDRESS + "TEXT NOT NULL," +
                COLUMN_FOLDER_ID + "INTEGER NOT NULL," +
                "FOREIGN KEY(" + COLUMN_FOLDER_ID + ") REFERENCES " + FOLDER_TABLE + "(" + COLUMN_FOLDER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE," +
                "PRIMARY KEY(" + COLUMN_ID + ")" +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE + "");
    }
}
