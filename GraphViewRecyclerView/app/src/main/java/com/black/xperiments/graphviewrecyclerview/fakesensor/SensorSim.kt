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
    fun starSensor(){
        handler.postDelayed(runnable, 0)
    }

    fun stopSensor(){
        handler.removeCallbacks(runnable)
    }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, time * 100)
            sensorStream?.dataA(random()*100)
            sensorStream?.dataB(random()*100)
            sensorStream?.dataC(random()*100)
            sensorStream?.dataD(random()*100)
            sensorStream?.dataE(random()*100)
//            Log.d("GraphViewRecyclerView","Sensor sim $signal")
        }
    }

    fun setSensorStream(sensorStream: SensorStream) {
        this.sensorStream = sensorStream
    }

}