package com.black.fragmenttoactivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstFragment fragment = new FirstFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mydemofragment, fragment);
        fragmentTransaction.commit();

        SecondFragment secondFragment = new SecondFragment();
        FragmentManager sfragmentManager = getSupportFragmentManager();
        FragmentTransaction sfragmentTransaction = sfragmentManager.beginTransaction();
        sfragmentTransaction.add(R.id.mydemofragment2, secondFragment);
        sfragmentTransaction.commit();

    }
    // You must override this method as the second Activity will always send its results to this Activity and then to the Fragment
    // You must override this method as the second Activity will always send its results to this Activity and then to the Fragment
 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
