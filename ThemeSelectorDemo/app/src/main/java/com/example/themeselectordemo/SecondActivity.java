package com.example.themeselectordemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_second);
        setTitle("SECOND ACTIVITY");
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
