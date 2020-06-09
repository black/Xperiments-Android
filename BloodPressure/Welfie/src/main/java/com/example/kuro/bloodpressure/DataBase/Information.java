package com.example.kuro.bloodpressure.DataBase;

/**
 * Created by Kuro on 4/19/2016.
 */
public class Information {
    public static final String USERID = "_id";
    public static final String NAME = "USERNAME";
    public static final String GENDER = "GENDER";
    public static final String WEIGHT = "WEIGHT";
    public static final String HEIGHT = "HEIGHT";
    public static final String AGE = "AGE";

    public static final String HISTORYID = "_id";
    public static final String BP = "BP";
    public static final String HEARTRATE = "HEARTRATE";
    public static final String DATETIME = "DATETIME";


    public static final String DATABASE_NAME_1 = "medical.db";
    public static final int DATABASE_VERSION_1 = 1;

    public static final String TABLE_PROFILE = "user_profile_data";
    public static final String TABLE_HISTORY = "user_history_data";

    public static final String CREATE_PROFILE_QUERY
            = "CREATE TABLE "
            + Information.TABLE_PROFILE
            + "(" + Information.USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Information.NAME + " TEXT, "
            + Information.GENDER + " TEXT, "
            + Information.WEIGHT + " TEXT, "
            + Information.HEIGHT + " TEXT, "
            + Information.AGE + " TEXT);";

    public static final String CREATE_HISTORY_QUERY
            = "CREATE TABLE "
            + Information.TABLE_HISTORY
            + "(" + Information.HISTORYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Information.BP + " TEXT, "
            + Information.HEARTRATE + " TEXT, "
            + Information.DATETIME + " TEXT);";

}
