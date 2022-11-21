package com.black.instadownloader.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DownloadViewModel : ViewModel() {
    private val uri: MutableLiveData<String> = MutableLiveData()
}