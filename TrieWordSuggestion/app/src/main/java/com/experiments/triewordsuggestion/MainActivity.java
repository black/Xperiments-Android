package com.experiments.triewordsuggestion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import net.andreinc.jasuggest.JaCacheConfig;
import net.andreinc.jasuggest.JaSuggest;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText enterWord;
    String[] words = { "us", "usa", "use", "useful", "useless", "user", "usurper" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JaCacheConfig jaCacheConfig =
                JaCacheConfig.builder()
                        .maxSize(512)
                        .build();

        final JaSuggest jaSuggest = JaSuggest.builder()
                .ignoreCase()
                .withCache(jaCacheConfig)
                .buildFrom(words);

       enterWord = findViewById(R.id.enter_word);
       enterWord.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               List<String> result = jaSuggest.findSuggestions(enterWord.getText().toString());
               Log.d("RESULT",result.toString());
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });
    }
}