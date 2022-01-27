package com.black.xperiments.kotlin_asynctask_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.black.xperiments.kotlin_asynctask_coroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val asyncExample: ExampleAsync by lazy { ViewModelProvider(this)[ExampleAsync::class.java] }
    private val handler = Handler(Looper.getMainLooper())
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun startAsyncTask(view: View) {
        doSomeSyncTask()
    }

    private fun doSomeSyncTask(){
        asyncExample.execute(onPreExecute = {
            binding.progressBarView.visibility = View.VISIBLE
        }, doInBackground = {
            counter++
            Thread.sleep(5000L) // simulate asyncTask, never use this in production app.
            someAsyncProcess(counter)
        }, onPostExecute = {
            binding.progressBarView.visibility = View.INVISIBLE
            it.apply {
                binding.resultsView.text = this
            }

        })
    }

    private fun someAsyncProcess(index:Int):String {
        return "$index Hello Universe! \uD83D\uDD90 \uD83D\uDD90"
    }

}