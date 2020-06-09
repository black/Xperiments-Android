package com.example.themeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CommonActivity extends AppCompatActivity {
    public int resultValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("res"))
            resultValue = getIntent().getIntExtra("res",0);

        switch(resultValue){
            case 0:
                setTheme(R.style.AppTheme);
                break;
            case 1:
                setTheme(R.style.MedicalThemeBlue);
                break;
            case 2:
                setTheme(R.style.MedicalThemeOrange);
                break;

        }
    }
}
