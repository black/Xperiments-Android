package com.example.canvasexample;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

public class MainActivity extends AppCompatActivity {

    long timeDelay = 1000;
    Handler handler = new Handler();
    boolean mStopHandler = false;

    GraphView mycanvas;
    int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mycanvas = findViewById(R.id.graphView);
        mycanvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mycanvas.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = mycanvas.getWidth();
                mycanvas.setMaxData(width/10);
                handler.post(runnable);
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* Updating progress */
            Random random = new Random();
            int data = random.nextInt(50) * (random .nextBoolean() ? -1 : 1);
            mycanvas.addData(data);
            if (!mStopHandler) {
                handler.postDelayed(this, timeDelay);
            }
        }
    };
}
