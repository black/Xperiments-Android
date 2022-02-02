package com.black.xperiments.graphviewrecyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.black.xperiments.graphviewrecyclerview.models.FusionStateData


class SomeViewModel: ViewModel() {
    private val sensorData: MutableLiveData<ArrayList<Double>> = MutableLiveData()
    private val fusionStateData: MutableLiveData<FusionStateData> = MutableLiveData()
    fun setSensorData(msg: ArrayList<Double>) {
        sensorData.postValue(msg)
    }
    fun getSensorData() = sensorData

    fun setFusionState(data: FusionStateData) {
        fusionStateData.postValue(data)
    }
    fun getFusionState() = fusionStateData
}