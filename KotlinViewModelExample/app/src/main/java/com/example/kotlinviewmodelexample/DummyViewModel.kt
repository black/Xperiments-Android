package com.example.kotlinviewmodelexample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class DummyViewModel:ViewModel(){

    val setRandomNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}
