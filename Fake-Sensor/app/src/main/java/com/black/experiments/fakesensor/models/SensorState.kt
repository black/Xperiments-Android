package com.black.experiments.fakesensor.models

data class SensorState(
    var powerSensorOne:Int=0,
    var powerSensorTwo:Int=0,
    var statusSensorOne:String="not_found",
    var statusSensorTwo:String="not_found")