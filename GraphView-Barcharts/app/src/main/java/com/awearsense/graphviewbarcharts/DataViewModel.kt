package com.awearsense.graphviewbarcharts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    var x:MutableLiveData<Int> = MutableLiveData()
    var y:MutableLiveData<Int> = MutableLiveData()
    var z:MutableLiveData<Int> = MutableLiveData()
    var segment:MutableLiveData<DoubleArray> = MutableLiveData()
    var fft:MutableLiveData<DoubleArray> = MutableLiveData()
}