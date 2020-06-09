package com.example.kuro.bloodpressure.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kuro on 4/18/2016.
 */

public class DateBaseHelper extends SQLiteOpenHelper {


    public DateBaseHelper(Context context) {
        super(context, Information.DATABASE_NAME_1, null, Information.DATABASE_VERSION_1);
        Log.e("DATABASE OPERATION", "Database created ...");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Information.CREATE_PROFILE_QUERY);
            db.execSQL(Information.CREATE_HISTORY_QUERY);
            Log.e("DATABASE OPERATION", "Table created / opened...");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXSITS" + Information.TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXSITS" + Information.TABLE_HISTORY);
        onCreate(db);
    }

    public long addUserInformation(String user_name, String gender, String weight, String height, String age) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues mcontentValues = new ContentValues();
        mcontentValues.put(Information.NAME, user_name);
        mcontentValues.put(Information.GENDER, gender);
        mcontentValues.put(Information.WEIGHT, weight);
        mcontentValues.put(Information.HEIGHT, height);
        mcontentValues.put(Information.AGE, age);
        long saved = mydb.insert(Information.TABLE_PROFILE, null, mcontentValues);
        return saved;
    }

    public long addUserHistory(String heart_rate, String bp, String datetime) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues mcontentValues = new ContentValues();
        mcontentValues.put(Information.BP, bp);
        mcontentValues.put(Information.HEARTRATE, heart_rate);
        mcontentValues.put(Information.DATETIME, datetime);
        long saved = mydb.insert(Information.TABLE_HISTORY, null, mcontentValues);
        return saved;
    }

    public Cursor getUserInformation() {
        SQLiteDatabase mydb = this.getReadableDatabase();
        String[] projection = {Information.NAME,Information.WEIGHT,Information.HEIGHT,Information.GENDER,Information.AGE};
//      Cursor mcursor = mydb.rawQuery("select * from " + Information.TABLE_NAME, null);
        Cursor mcursor = mydb.query(Information.TABLE_PROFILE, projection, null, null, null, null, null);
        return mcursor;
    }

    public Cursor getUserHistory() {
        SQLiteDatabase mydb = this.getReadableDatabase();
        String[] projection = {Information.BP,Information.HEARTRATE,Information.DATETIME};
        Cursor mcursor = mydb.query(Information.TABLE_HISTORY,projection,null,null,null,null,null);
        return mcursor;
    }

    public  Cursor getLastEntry(){
        SQLiteDatabase mydb = this.getReadableDatabase();
        String[] projection = {Information.BP,Information.HEARTRATE,Information.DATETIME};
        Cursor mcursor = mydb.query(Information.TABLE_HISTORY, projection, null, null, null, null, null);
        mcursor.moveToLast();
        return mcursor;
    }

    public  Cursor getSpecificEntry(String id){
        SQLiteDatabase mydb = this.getReadableDatabase();
        String query = "SELECT * FROM "+Information.TABLE_PROFILE+" WHERE " + Information.USERID + " = " + id;
        Cursor mcursor = mydb.rawQuery(query, null);
        return mcursor;
    }
}


