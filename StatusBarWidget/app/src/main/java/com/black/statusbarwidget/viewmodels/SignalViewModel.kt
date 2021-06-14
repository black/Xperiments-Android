package com.black.statusbarwidget.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignalViewModel : ViewModel() {


    private val meraki: MutableLiveData<Int> = MutableLiveData()
    private val tonoka: MutableLiveData<Int> = MutableLiveData()

    // Focus
    fun setMeraki(value: Int) {
        meraki.value = value
    }
    fun getMeraki() = meraki

    // Calmness
    fun setTonoka(value: Int) {
        tonoka.value = value
    }
    fun getTonoka() = tonoka


    override fun onCleared() {
        super.onCleared()
    }
}