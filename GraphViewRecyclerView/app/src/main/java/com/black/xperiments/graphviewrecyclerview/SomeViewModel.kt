package com.black.xperiments.graphviewrecyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SomeViewModel:ViewModel() {
    private val sensorA: MutableLiveData<Int> = MutableLiveData()
    private val sensorB: MutableLiveData<Int> = MutableLiveData()
    private val sensorC: MutableLiveData<Int> = MutableLiveData()
    private val sensorD: MutableLiveData<Int> = MutableLiveData()
    private val sensorE: MutableLiveData<Int> = MutableLiveData()

    fun setSensorA(msg: Int) {
        sensorA.postValue(msg)
    }
    fun getSensorA() = sensorA

    fun setSensorB(msg: Int) {
        sensorB.postValue(msg)
    }
    fun getSensorB() = sensorB

    fun setSensorC(msg: Int) {
        sensorC.postValue(msg)
    }
    fun getSensorC() = sensorC

    fun setSensorD(msg: Int) {
        sensorD.postValue(msg)
    }
    fun getSensorD() = sensorD

    fun setSensorE(msg: Int) {
        sensorE.postValue(msg)
    }
    fun getSensorE() = sensorE

}