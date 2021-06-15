package com.black.statusbarwidget.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignalViewModel : ViewModel() {


    private val meraki: MutableLiveData<Int> = MutableLiveData()
    private val tonoka: MutableLiveData<Int> = MutableLiveData()
    private val gorku: MutableLiveData<Int> = MutableLiveData()


    fun setMeraki(value: Int) {
        meraki.value = value
    }
    fun getMeraki() = meraki


    fun setTonoka(value: Int) {
        tonoka.value = value
    }
    fun getTonoka() = tonoka


    fun setGorku(value: Int) {
        gorku.value = value
    }
    fun getGorku() = gorku


    override fun onCleared() {
        super.onCleared()
    }
}