package com.example.asyncprogressbarupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressUpdate);
        textView = findViewById(R.id.counter);

    }

    public void startProgress(View view) {
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<10;i++){
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalI);
                            textView.setText(finalI +"");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
       // background.stop();
        background.start();

        for(int i=0;i<100;i++) {
            Log.d("Test",i+"");
        }

    }
}
