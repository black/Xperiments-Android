package com.black.overlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    /*
     An app which draws over other apps for some kind of interaction which usually can be seen in screen recording app, screen sharing app, music play, youtube flaoting window etc.
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}