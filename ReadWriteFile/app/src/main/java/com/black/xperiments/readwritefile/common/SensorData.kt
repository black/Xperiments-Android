package com.black.xperiments.readwritefile.common

class SensorData(private val data: String) {
    fun toCSVData(): String {
        val time: Long = System.currentTimeMillis()
        return "$time,$data\n"
    }
}
