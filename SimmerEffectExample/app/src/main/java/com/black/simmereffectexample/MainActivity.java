package com.black.simmereffectexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

    ShimmerFrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        container.startShimmerAnimation();

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.stopShimmerAnimation();
            }
        });
    }
}
