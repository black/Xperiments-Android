package com.black.experiments.menuicons.sensor

import com.black.experiments.menuicons.observers.Power

interface PowerInterface {
    fun onData(power: Int)
}