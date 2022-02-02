package com.black.xperiments.graphviewrecyclerview.fakesensor

interface SensorStream {
    fun onData(signal:ArrayList<Double>)
}