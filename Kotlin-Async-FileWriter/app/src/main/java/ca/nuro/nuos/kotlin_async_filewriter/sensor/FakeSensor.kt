package ca.nuro.nuos.kotlin_async_filewriter.sensor

import android.os.Handler
import android.os.Looper

class FakeSensor{
    private var handler = Handler(Looper.getMainLooper())
    private var dataInterface: DataInterface? = null

    fun startSensor(){
        handler.postDelayed(runnable,0)
    }

    fun stopSensor(){
        handler.removeCallbacks(runnable)
    }
    private val runnable = object: Runnable {
        override fun run() {
            for (i in 0 until 1024){
                val data = (Math.random()*100).toInt()
                dataInterface?.onData(data)
            }
            handler.postDelayed(this, 0)
        }
    }

    fun setDataListener(listener: DataInterface) {
        dataInterface = listener
    }

}