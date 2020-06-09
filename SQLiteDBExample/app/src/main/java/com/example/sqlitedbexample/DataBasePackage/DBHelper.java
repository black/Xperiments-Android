package com.example.sqlitedbexample.DataBasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydb";
    private static final String TABLE_NAME = "QUESTIONS";
    private static final String COLUMN_NAME = "QUESTION";
    private static final int VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE QUESTIONS (_id INTEGER PRIMARY KEY AUTOINCREMENT, QUESTION TEXT)";
        db.execSQL(sql);

        /*insert*/
        insertData("How are you?",db);
        insertData("Do you like iceream?",db);
        insertData("Kenny was first president?",db);
        insertData("Jake gayel is an actress?",db);
        insertData("Did germany win the war?",db);
        insertData("Apples are vegitable?",db);
        insertData("Milk is white?",db);
        insertData("Do you have pain?",db);
        insertData("Papaya is a tree?",db);
        insertData("Is is raining?",db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }

    /*--------Methods--------*/
    private void insertData(String question,SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put("QUESTION",question);
        database.insert("QUESTIONS",null,values);
    }

}
