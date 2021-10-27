package com.black.xperiments.headtracking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jins_jp.meme.MemeRealtimeData

class SensorViewModel: ViewModel()  {
    /*-----------------EOG------------------*/
    private val eogPower: MutableLiveData<Int> = MutableLiveData()
    private val eogConnect: MutableLiveData<String> = MutableLiveData()
    private val eogAllData: MutableLiveData<MemeRealtimeData> = MutableLiveData()

    /*-----------------EOG------------------*/

    // EEG Connection Status
    fun setEOGConnect(msg: String) {
        eogConnect.postValue(msg)
    }
    fun getEOGConnect() = eogConnect

    // EOG Power
    fun setEOGPower(msg: Int) {
        eogPower.postValue(msg)
    }
    fun getEOGPower() = eogPower

    // EEG Connection Status
    fun setEOGAllData(msg: MemeRealtimeData) {
        eogAllData.postValue(msg)
    }
    fun getEOGAllData() = eogAllData

}