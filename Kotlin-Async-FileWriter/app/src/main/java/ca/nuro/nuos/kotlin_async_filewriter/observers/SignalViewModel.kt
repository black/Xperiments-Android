package ca.nuro.nuos.kotlin_async_filewriter.observers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.nuro.nuos.kotlin_async_filewriter.models.FileName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignalViewModel : ViewModel() {

    private val fakeSignal: MutableLiveData<Int> = MutableLiveData()
    private val fileName: MutableLiveData<FileName> = MutableLiveData()

    // Connection Status
    fun setFakeSignal(value: Int) {
        fakeSignal.postValue(value)
    }
    fun getFakeSignal() = fakeSignal

    // file Name
    fun setFileName(value: FileName) {
        fileName.postValue(value)
    }
    fun getFileName() = fileName

    fun setSensorData(data:Int): Flow<Int> {
        return flow{
            emit(data)
        }
    }

}