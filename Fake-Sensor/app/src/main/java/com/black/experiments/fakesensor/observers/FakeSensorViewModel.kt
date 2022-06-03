package com.black.experiments.fakesensor.observers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FakeSensorViewModel : ViewModel() {

    var fakeConnectionOne: MutableLiveData<String> = MutableLiveData()
    var fakeSignalOne: MutableLiveData<Int> = MutableLiveData()
    var fakePowerOne: MutableLiveData<Int> = MutableLiveData()

    var fakeConnectionTwo: MutableLiveData<String> = MutableLiveData()
    var fakeSignalTwo: MutableLiveData<Int> = MutableLiveData()
    var fakePowerTwo: MutableLiveData<Int> = MutableLiveData()
}