package com.example.sharepreferencenew.DataRepository;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "UNVERSALDEMO";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_SPEED_ONE = "speed_one";
    private static final String KEY_SPEED_TWO = "speed_two";
    private static final String KEY_SPEED_ZERO = "speed_zero";

    public SharedPrefManager(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setStateObject(StateObject stateStatus){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LEVEL, stateStatus.getLevel());
        editor.putFloat(KEY_SPEED_ZERO, stateStatus.getSpeed_zero());
        editor.putFloat(KEY_SPEED_ONE, stateStatus.getSpeed_one());
        editor.putFloat(KEY_SPEED_TWO, stateStatus.getSpeed_two());
        editor.apply();
    }

    public StateObject getStateObject(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        StateObject stateObject = new StateObject();
        stateObject.setLevel(sharedPreferences.getInt(KEY_LEVEL, 0));
        stateObject.setSpeed_zero(sharedPreferences.getFloat(KEY_SPEED_ZERO, 0.5f));
        stateObject.setSpeed_one(sharedPreferences.getFloat(KEY_SPEED_ONE, 0.5f));
        stateObject.setSpeed_two(sharedPreferences.getFloat(KEY_SPEED_TWO, 0.5f));
        return stateObject;
    }
}
