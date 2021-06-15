package com.black.vectoranimation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    AnimationDrawable rocketAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView rocketImage = (ImageView) findViewById(R.id.animationView);
        rocketImage.setBackgroundResource(R.drawable.rocket_thrust);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        rocketAnimation.start();

        /* Working Code */
        ImageView rocketImage = findViewById(R.id.animationView);
        Glide.with(this).load(R.drawable.emo).into(rocketImage);


        ImageView assetImage = findViewById(R.id.assetView);
        Glide.with(getApplicationContext()).load(Uri.parse("file:///android_asset/001.png")).into(assetImage);

        AnimationDrawable animPlaceholder = (AnimationDrawable) getApplicationContext().getDrawable(R.drawable.loading);
        animPlaceholder.start(); // probably needed

        ImageView animeView = findViewById(R.id.animeView);
        Glide.with(getApplicationContext())
                .load((Bitmap) null)
                .placeholder(animPlaceholder)
                .into(animeView);
    }
}
