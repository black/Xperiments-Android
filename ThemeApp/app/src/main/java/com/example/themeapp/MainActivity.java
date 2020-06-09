package com.example.themeapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MAIN");
        setContentView(R.layout.activity_main);
    }

    public void launchButtonMain(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        intent.putExtra("res",resultValue);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void launchFragment(View view) {
        loadFragment(new TestFragment());
    }
    private void loadFragment(Fragment fragment) {
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();*/
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
