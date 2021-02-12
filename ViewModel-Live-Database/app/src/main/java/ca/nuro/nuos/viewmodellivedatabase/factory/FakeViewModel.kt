package ca.nuro.nuos.viewmodellivedatabase.factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FakeViewModel : ViewModel() {
    private val dataList: MutableLiveData<ArrayList<Nu>> = MutableLiveData()

    //-----Play Message
    fun setDataList(dart: ArrayList<Nu>) {
        dataList.value = dart
    }

    /*fun getDataList(): LiveData<ArrayList<Nu>> {
        return dataList
    }*/

    fun getDataList() = dataList

    override fun onCleared() {
        super.onCleared()
    }
}