package com.example.exampleviewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.exampleviewpager2.Fragments.FragmentOne;
import com.example.exampleviewpager2.Fragments.FragmentThree;
import com.example.exampleviewpager2.Fragments.FragmentTwo;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VP2Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.vp2example);
        myAdapter = new VP2Adapter(getSupportFragmentManager(),getLifecycle());


        viewPager2.setAdapter(myAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}