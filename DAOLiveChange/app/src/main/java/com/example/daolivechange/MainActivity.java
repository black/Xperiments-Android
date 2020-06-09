package com.example.daolivechange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBarA,seekBarB,seekBarO;
    private TextView seekBarAT,seekBarBT,seekBarOT;
    private DataObject dataObject = new DataObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarA = findViewById(R.id.apple);
        seekBarB = findViewById(R.id.banana);
        seekBarO = findViewById(R.id.orange);

        seekBarAT = findViewById(R.id.appleT);
        seekBarBT = findViewById(R.id.bananaT);
        seekBarOT = findViewById(R.id.orangeT);

        seekBarAT.setText(dataObject.getApple()+"");
        seekBarBT.setText(dataObject.getBanana()+"");
        seekBarOT.setText(dataObject.getOrange()+"");

        seekBarA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dataObject.setApple(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dataObject.setBanana(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarO.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dataObject.setOrange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
