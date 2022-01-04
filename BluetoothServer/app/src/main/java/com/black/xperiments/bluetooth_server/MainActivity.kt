package com.black.xperiments.bluetooth_server

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    private val UUID = "e012079a-6bfd-11ec-90d6-0242ac120003"
    private val handler= Handler(Looper.getMainLooper())
    private var counter = 0
    private var time = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // speed
    fun starScan(){
        handler.postDelayed(runnable, 0)
    }

    fun stopScan(){
        handler.removeCallbacks(runnable)
    }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, time * 1000)
            if(counter<5)counter++
            else counter = 0
        }
    }

}