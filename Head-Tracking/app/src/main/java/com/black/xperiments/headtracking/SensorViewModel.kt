package com.black.xperiments.headtracking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jins_jp.meme.MemeRealtimeData

class SensorViewModel: ViewModel()  {
    /*-----------------EOG------------------*/
    private val eogPower: MutableLiveData<Int> = MutableLiveData()
    private val eogConnect: MutableLiveData<String> = MutableLiveData()
    private val eogAllData: MutableLiveData<MemeRealtimeData> = MutableLiveData()
    private val tileIndex: MutableLiveData<Int> = MutableLiveData()
    private val trigger: MutableLiveData<String> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()

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

    // Tile index
    fun setHighLightTileIndex(msg: Int) {
        tileIndex.postValue(msg)
    }
    fun getHighLightTileIndex() = tileIndex


    // Tile trigger
    fun setTrigger(msg: String) {
        trigger.value = msg
    }
    fun getTrigger() = trigger

    //-----Play Message
    fun setMessage(msg: String) {
        message.value = msg
    }

    fun getMessage() = message

}