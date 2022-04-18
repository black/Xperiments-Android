package com.black.experiments.menuicons.sensor

import android.os.Handler
import android.os.Looper
import android.util.Log

class FakeSensor(var power:Int, var del:Long){
    private var handler = Handler(Looper.getMainLooper())
    private var statusInterface:StatusInterface? = null
    private var dataInterface: DataInterface? = null
    private var powerInterface: PowerInterface? = null
    private var status  =  arrayOf("not_found","started","connecting","connected","disconnected")
    private var statusIdx = 0
    private var currPower = 0

    init {
        currPower = power
    }

    fun  startConnection(){
        statusIdx = 0
        power = currPower
        handler.removeCallbacks(statusRunnable)
        handler.postDelayed(statusRunnable,0)
    }

    fun startSensor(){
        handler.postDelayed(dataRunnable,0)
        handler.postDelayed(powerRunnableOne,0)
    }

    fun stopSensor(){
        handler.removeCallbacks(dataRunnable)
        statusInterface?.onStatus(status[statusIdx])
    }

    private val statusRunnable = object :Runnable{
        override fun run() {
            if(statusIdx<status.size-1){
                statusInterface?.onStatus(status[statusIdx])
                statusIdx++
                handler.postDelayed(this, 1000)
            }
            else removeStatusCallback()
        }
    }


    private val dataRunnable = object: Runnable {
        override fun run() {
            val data = (Math.random()*100).toInt()
            dataInterface?.onData(data)
            handler.postDelayed(this, 0)
        }
    }

    private val powerRunnableOne = object: Runnable {
        override fun run() {
            if(power>0){
                power--
                powerInterface?.onData(power)
                handler.postDelayed(this, del)
            }else removePowerCallback()
        }
    }

    private fun removeStatusCallback(){
        handler.removeCallbacks(statusRunnable)
    }

    private fun removePowerCallback(){
        handler.removeCallbacks(powerRunnableOne)
    }

    fun setConnectionStatusListener(statusListener: StatusInterface) {
        statusInterface = statusListener
    }

    fun setDataListener(dataListener: DataInterface) {
        dataInterface = dataListener
    }

    fun setPowerListener(powerListener: PowerInterface) {
        powerInterface = powerListener
    }
}