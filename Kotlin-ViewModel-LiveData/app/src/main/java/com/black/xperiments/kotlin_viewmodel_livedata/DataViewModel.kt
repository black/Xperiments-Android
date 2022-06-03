package com.black.xperiments.kotlin_viewmodel_livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel:ViewModel() {
    var dataobj: MutableLiveData<DataObj> = MutableLiveData()
    var randomdata: MutableLiveData<Int> = MutableLiveData()
}