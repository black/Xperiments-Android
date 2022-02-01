package com.black.xperiments.dspwithneurosky.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignalViewModel : ViewModel() {
    private val connection: MutableLiveData<String> = MutableLiveData()
    private val signalStrength: MutableLiveData<Int> = MutableLiveData()
    private val power: MutableLiveData<Int> = MutableLiveData()

    private val focus: MutableLiveData<Int> = MutableLiveData()
    private val calm: MutableLiveData<Int> = MutableLiveData()
    private val appreciation: MutableLiveData<Int> = MutableLiveData()
    private val mentalEffort: MutableLiveData<Int> = MutableLiveData()
    private val familiarity: MutableLiveData<Int> = MutableLiveData()

    private val raw: MutableLiveData<Int> = MutableLiveData()
    private val bands: MutableLiveData<IntArray> = MutableLiveData()

    // Connection Status
    fun setConnection(value: String) {
        connection.value = value
    }
    fun getConnection() = connection

    // power
    fun setPower(value: Int) {
        power.value = value
    }
    fun getPower() = power

    // Signal Strength
    fun setSignalStrength(value: Int) {
        signalStrength.value = value
    }
    fun getSignalStrength() = signalStrength

    // Focus
    fun setFocus(value: Int) {
        focus.value = value
    }
    fun getFocus() = focus

    // Calmness
    fun setCalm(value: Int) {
        calm.value = value
    }
    fun getCalm() = calm

    // Appreciation
    fun setAppreciation(value: Int) {
        appreciation.value = value
    }
    fun getAppreciation() = appreciation

    // Mental Effort
    fun setMentalEffort(value: Int) {
        mentalEffort.value = value
    }
    fun getMentalEffort() = mentalEffort

    // Familiarity
    fun setFamiliarity(value: Int) {
        familiarity.value = value
    }
    fun getFamiliarity() = familiarity

    // Raw
    fun setRaw(value: Int) {
        raw.value = value
    }
    fun getRaw()= raw

    // Bands
    fun setBands(value: IntArray) {
        bands.value = value
    }
    fun getBands() = bands


}