package ca.nuro.databindingkotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FakeDataViewModel:ViewModel() {
    private val one: MutableLiveData<String> = MutableLiveData()
    private val two: MutableLiveData<Int> = MutableLiveData()

    fun setOne(msg: String) {
        one.value = msg
    }
    fun getOne() = one

    fun setTwo(msg: Int) {
        two.value = msg
    }
    fun getTwo() = two

}