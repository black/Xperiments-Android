package com.black.xperiments.my_new_library

import android.util.Log

class MyLibrary {
    private var TAG = "MY_CUSTOM_LIBRARY"

    fun see(message:String){
        Log.d(TAG,message)
    }

    fun blind(value:Int):String{
        return if(value>0) "hello" else "bye bye"
    }
}