package ca.nuro.nuos.nurokotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignalViewModel : ViewModel() {
    private val connection: MutableLiveData<String> = MutableLiveData()
    private val signalStrength: MutableLiveData<Int> = MutableLiveData()
    private val focus: MutableLiveData<Int> = MutableLiveData()
    private val calm: MutableLiveData<Int> = MutableLiveData()
    private val raw: MutableLiveData<Int> = MutableLiveData()
    private val bands: MutableLiveData<IntArray> = MutableLiveData()
    private val other: MutableLiveData<IntArray> = MutableLiveData()
    private val motion: MutableLiveData<IntArray> = MutableLiveData()

    fun setConnection(value: String) {
        connection.value = value
    }

    fun getConnection(): LiveData<String> {
        return connection
    }

    fun setSignalStrength(value: Int) {
        signalStrength.value = value
    }

    fun getSignalStrength(): LiveData<Int> {
        return signalStrength
    }

    fun setFocus(value: Int) {
        focus.value = value
    }

    fun getFocus(): LiveData<Int> {
        return focus
    }

    fun setCalm(value: Int) {
        calm.value = value
    }

    fun getCalm(): LiveData<Int> {
        return calm
    }

    fun setRaw(value: Int) {
        raw.value = value
    }

    fun getRaw(): LiveData<Int> {
        return raw
    }

    fun setBands(value: IntArray) {
        bands.value = value
    }

    fun getBands(): LiveData<IntArray> {
        return bands
    }

    fun setOther(value: IntArray) {
        other.value = value
    }

    fun getOther(): LiveData<IntArray> {
        return other
    }

    fun setMotion(value: IntArray) {
        motion.value = value
    }

    fun getMotion(): LiveData<IntArray> {
        return motion
    }

    override fun onCleared() {
        super.onCleared()
    }
}