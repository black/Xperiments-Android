package com.black.xperiments.graphviewrecyclerview.fakesensor

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.lang.Math.random

class SensorSim constructor(
    var context: Context,
){
    private var sensorStream: SensorStream?=null
    private var time = 1L
    private val handler= Handler(Looper.getMainLooper())

    init {
    }

    // speed
    fun startSensor(){
        handler.postDelayed(runnable, 0)
    }

    fun stopSensor(){
        handler.removeCallbacks(runnable)
    }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, time)
            sensorStream?.onData(arrayListOf<Double>(random()*100,random()*100,random()*100,random()*100))
        }
    }

    fun setSensorStream(sensorStream: SensorStream) {
        this.sensorStream = sensorStream
    }

}