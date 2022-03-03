package com.example.bread

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast

class Bread(var context: Context, var toast_layout:Int, var message:String, var duration: Long) {
    private val handler = Handler(Looper.getMainLooper())
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        handler.postDelayed({

        },duration)
    }

    fun show(){

    }

}