package ca.nuro.nuos.interfacelivedata

import android.os.Handler

class Dummy( var handler: Handler){
    fun getData(data:DataInterface){
        handler.post(object :Runnable{
            override fun run() {
                val signal = (Math.random()*100).toInt()
                data.onData(signal)
                handler.postDelayed(this,100)
            }
        })
    }

    fun getTrigData(trigger:Int,data: DataInterface){
        data.onData(trigger*10)
    }
}