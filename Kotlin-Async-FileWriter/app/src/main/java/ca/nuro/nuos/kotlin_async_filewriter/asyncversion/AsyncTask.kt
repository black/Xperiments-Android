package ca.nuro.nuos.kotlin_async_filewriter.asyncversion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsyncTask:ViewModel() {
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