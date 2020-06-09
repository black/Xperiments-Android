package com.example.themeselectordemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        setTitle("MAIN ACTIVITY");
    }

    public void nextActivity(View view){
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.black:
                ThemeUtils.changeToTheme(this, ThemeUtils.BLACK);
                break;
            case R.id.blue:
                ThemeUtils.changeToTheme(this, ThemeUtils.BLUE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

/*https://www.developer.com/ws/android/changing-your-android-apps-theme-dynamically.html*/