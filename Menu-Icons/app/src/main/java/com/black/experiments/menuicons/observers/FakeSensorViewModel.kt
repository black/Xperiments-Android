package com.black.experiments.menuicons.observers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FakeSensorViewModel : ViewModel() {

    private val fakeConnectionOne: MutableLiveData<String> = MutableLiveData()
    private val fakeSignalOne: MutableLiveData<Int> = MutableLiveData()
    private val fakePowerOne: MutableLiveData<Int> = MutableLiveData()

    private val fakeConnectionTwo: MutableLiveData<String> = MutableLiveData()
    private val fakeSignalTwo: MutableLiveData<Int> = MutableLiveData()
    private val fakePowerTwo: MutableLiveData<Int> = MutableLiveData()

    // Connection Status
    fun setFakeSensorOneConnectionStatus(value: String) {
        fakeConnectionOne.postValue(value)
    }
    fun getFakeSensorOneConnectionStatus() = fakeConnectionOne

    fun setFakeOneSignal(value: Int) {
        fakeSignalOne.postValue(value)
    }
    fun getFakeOneSignal() = fakeSignalOne

    // file Name
    fun setFakeOnePower(value: Int) {
        fakePowerOne.postValue(value)
    }
    fun getFakeOnePower() = fakePowerOne

    //----------------------------------

    // Connection Status
    fun setFakeSensorTwoConnectionStatus(value: String) {
        fakeConnectionTwo.postValue(value)
    }
    fun getFakeSensorTwoConnectionStatus() = fakeConnectionTwo

    fun setFakeTwoSignal(value: Int) {
        fakeSignalTwo.postValue(value)
    }
    fun getFakeTwoSignal() = fakeSignalTwo

    // file Name
    fun setFakeTwoPower(value: Int) {
        fakePowerTwo.postValue(value)
    }
    fun getFakeTwoPower() = fakePowerTwo

}