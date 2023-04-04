package com.xperiments.mousetape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.InputDevice
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if((event?.source?.and(InputDevice.SOURCE_CLASS_POINTER))!=0){
            when(event?.action){
                MotionEvent.ACTION_SCROLL -> {
                    if(event.getAxisValue(MotionEvent.AXIS_SCROLL) <  0.0f){
                        Log.d("SCROLL" ,"DOWN")
                    }else{
                        Log.d("SCROLL" ,"UP")
                    }
                    return  true
                }
            }
        }
        return super.onGenericMotionEvent(event)
    }
}