package com.example.themeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class Settings extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SETTINGS");
        setContentView(R.layout.activity_settings);

        RadioGroup themeSelector = findViewById(R.id.themeselector);
        themeSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.defaultTheme:
                        launch(0);
                        break;
                    case R.id.blueTheme:
                        launch(1);
                        break;
                    case R.id.orangeTheme:
                        launch(2);
                        break;

                }
            }
        });

    }

    public void launch(int value) {
        Intent intent = new Intent(Settings.this,MainActivity.class);
        intent.putExtra("res",value);
        startActivity(intent);
        finish();
    }
}
