package com.example.yesnogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {
    /* APIS */
    private TextToSpeech engine;
    private String[] buttonLables = {"yes","no","maybe","prev"};
    private int counter = 0;
    private TextView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        engine = new TextToSpeech(this,this);

        questionView = findViewById(R.id.question);
        questionView.setText(getString(getResources().getIdentifier("question"+0, "string", getPackageName())));

        final RelativeLayout answersLayout = findViewById(R.id.answers);
        final RelativeLayout radarCanvas = findViewById(R.id.radarCanvas);
        answersLayout.post(new Runnable() {
            @Override
            public void run() {
                BasicRadar basicRadar = new BasicRadar(MainActivity.this, answersLayout, radarCanvas);
                radarCanvas.addView(basicRadar);
            }
        });

        /* set onclick listener for buttons*/
        for (String buttonLable : buttonLables) {
            ((LinearLayout) findViewById(getResources().getIdentifier(buttonLable, "id", getPackageName()))).setOnClickListener(this);
        }
    }

    @Override
    public void onDestroy() {
        if (engine != null) {
            engine.stop();
            engine.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int results = engine.setLanguage(Locale.US);
            if (results == TextToSpeech.LANG_MISSING_DATA
                    || results == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Not supported", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* Play an Audio Message */
    private void PlayMessage(String msg) {
        engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes:
                PlayMessage("Yes");
                if(counter<10)counter++;
                break;
            case R.id.no:
                PlayMessage("No");
                if(counter<10)counter++;
                break;
            case R.id.maybe:
                PlayMessage("maybe");
                if(counter<10)counter++;
                break;
            case R.id.prev:
                PlayMessage("Prev");
                if(counter>0)counter--;
                break;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                questionView.setText(getString(getResources().getIdentifier("question"+counter, "string", getPackageName())));
                questionView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.flashbg));
            }
        }, 1500);
    }
}
