package com.yumelabs.stateflow_livedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    // https://www.youtube.com/watch?v=Qk2mIpE_riY&t=105s
    private var _fakeState = MutableStateFlow<FakeState>(FakeState.Empty)
    val fakeState:StateFlow<FakeState> = _fakeState

    fun fakeMethod(data1:String,data2:String) = viewModelScope.launch {
        _fakeState.value = FakeState.Loading
        delay(2000L)
        if(data1=="root" && data2 =="topsecret"){
            _fakeState.value = FakeState.Success
        }else{
            _fakeState.value = FakeState.Error("Wrong Data")
        }
    }


    sealed class FakeState{ // similar to enum
        object Success:FakeState()
        data class Error(val message:String):FakeState()
        object Loading:FakeState()
        object Empty:FakeState()
    }

}