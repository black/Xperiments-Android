package com.black.xperiments.kotlin_asynctask_coroutine

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExampleAsync:ViewModel() {
    fun <R> execute(
        onPreExecute:()->Unit,
        doInBackground:()->R,
        onPostExecute:(R)->Unit
    ) = viewModelScope.launch {
        onPreExecute()
        val res = withContext(Dispatchers.IO){
            doInBackground()
        }
        onPostExecute(res)
    }
}