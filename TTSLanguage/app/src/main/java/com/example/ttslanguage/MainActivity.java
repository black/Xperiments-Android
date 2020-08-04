package com.example.ttslanguage;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity  implements TextToSpeech.OnInitListener {
    private static final int TTS_DATA_CHECK = 1;
    private TextToSpeech engine;
    private EditText textMsg;
    private RadioGroup selectLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        engine = new TextToSpeech(this,this);
        textMsg = findViewById(R.id.textMsg);
        selectLanguage = findViewById(R.id.selectLanguage);
        selectLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("RadioPosition",i+"");
                    switch (i){
                        case 1:
                            engine.setLanguage(Locale.ENGLISH);
                            break;
                        case 2:
                            engine.setLanguage(Locale.FRENCH);
                            break;
                    }
            }
        });
    }

    public void playText(View view) {
        engine.speak(textMsg.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
    }


    @Override
    public void onStop() {
        super.onStop();
//        if (engine != null) {
//            engine.shutdown();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (engine != null) {
//            engine.shutdown();
//        }
    }

    @Override
    public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS){
            int result = engine.setLanguage(Locale.ENGLISH);
            if(result == TextToSpeech.LANG_MISSING_DATA
                    || result== TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"Not supported",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"TTS is missing",Toast.LENGTH_LONG).show();
        }
    }

}