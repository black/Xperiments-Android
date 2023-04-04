package com.experiments.imusensor.datamodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel:ViewModel()  {
    val pitch: MutableLiveData<Float> = MutableLiveData()
    val yaw: MutableLiveData<Float> = MutableLiveData()
    val roll: MutableLiveData<Float> = MutableLiveData()

    val x: MutableLiveData<Float> = MutableLiveData()
    val y: MutableLiveData<Float> = MutableLiveData()
    val z: MutableLiveData<Float> = MutableLiveData()

    val permission: MutableLiveData<Boolean> = MutableLiveData()

}