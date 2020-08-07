package com.example.autocompletetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    private static final String[] COUNTRIES = new String[]{
        "Afganistan","Albania","Bangkok","Bangalore","Calcutta","Ciria","India","Imphal"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiAutoCompleteTextView editView = findViewById(R.id.auto_comp);
        ArrayAdapter<String> adapter   = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,COUNTRIES);
        editView.setAdapter(adapter);
        editView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}  