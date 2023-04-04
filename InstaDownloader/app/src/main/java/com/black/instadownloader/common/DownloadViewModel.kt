package com.black.instadownloader.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DownloadViewModel : ViewModel() {
    val uri: MutableLiveData<String> = MutableLiveData()
    val response: MutableLiveData<String> = MutableLiveData()
}