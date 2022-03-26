package ca.nuro.nuos.kotlin_async_filewriter.asyncversion

import android.os.AsyncTask

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}