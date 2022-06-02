package com.black.xperiments.readwriteshare.common

class SensorData(private val data: String) {
    fun toCSVData(): String {
        val time: Long = System.currentTimeMillis()
        return "$time,$data\n"
    }
}
