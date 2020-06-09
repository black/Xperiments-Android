package com.example.onboardingexample;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout pageIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full Screen App
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide Action Bar
         getSupportActionBar().hide();

        setContentView(R.layout.activity_intro);

        pageIndicators = findViewById(R.id.tabLayout);

        List<ScreenItem> pagelist = new ArrayList<>();

        pagelist.add(new ScreenItem("One","One Items",R.drawable.ic_arrow_forward_black_24dp));
        pagelist.add(new ScreenItem("Two","Two Items",R.drawable.ic_arrow_forward_black_24dp));
        pagelist.add(new ScreenItem("Three","Three Items",R.drawable.ic_arrow_forward_black_24dp));

        screenPager = findViewById(R.id.introPages);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,pagelist);
        screenPager.setAdapter(introViewPagerAdapter);

        pageIndicators.setupWithViewPager(screenPager);
    }
}
