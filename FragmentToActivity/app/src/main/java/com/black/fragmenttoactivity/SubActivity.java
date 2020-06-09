package com.black.fragmenttoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SubActivity extends AppCompatActivity {
    private String message = "";
    private int RESULT_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        message = intent.getStringExtra("val");
        RESULT_CODE = intent.getIntExtra("resultCode", 0);
        Log.d("RESULTS ACTIVITY", message + " \t" + RESULT_CODE);
    }

    public void goBackToFragment(View view) {
        Intent intent = new Intent();
        intent.putExtra("result", "Returned results to " + message);
        setResult(RESULT_CODE, intent); // You can also send result without any data using setResult(int resultCode)
        finish();
    }
}
