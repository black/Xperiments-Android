package com.black.statusbarwidget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.black.statusbarwidget.NotificationWidget.Companion.CHANNEL_1_ID
import com.black.statusbarwidget.viewmodels.SignalViewModel


@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {

    private val signalViewModel: SignalViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private var writing = true

    var animationDrawable = AnimationDrawable()

    private var notificationManager:NotificationManagerCompat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager =  NotificationManagerCompat.from(this)
    }

    override fun onStart() {
        super.onStart()
        observers()
    }

    private fun observers(){
        signalViewModel.getMeraki().observe(this,{
            Log.d("NotificationValue","Meraki ${it}")
            val merakiNotification = NotificationCompat.Builder(this,CHANNEL_1_ID).apply {
                setContentTitle("Meraki ${it}")
                val icon = resources.getIdentifier("ic_power_$it", "drawable", packageName)
                setSmallIcon(icon)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }

            val PROGRESS_MAX = 100
            NotificationManagerCompat.from(this).apply {
//                merakiNotification.setProgress(PROGRESS_MAX, it, false)
//                merakiNotification.setContentText("Max Meraki Achieved")
//                    .setProgress(0, 0, false)
                notify(1, merakiNotification.build())
            }

        })

        signalViewModel.getTonoka().observe(this,{
            /*Log.d("NotificationValue","Tonoka ${it}")
            val tonokaNotification = NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_accessibility_24)
                .setContentTitle("Tonoka ${it}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            notificationManager?.notify(1,tonokaNotification)*/
        })
    }

    private var i: Int = 0
    private val runnableCode = object: Runnable {
        override fun run() {
            if(i<3)i += 1
            else i=0
            signalViewModel.setMeraki(i)
            signalViewModel.setTonoka(i * 2)
            handler.postDelayed(this, 500)
        }
    }
    private fun startWriting() {
        if (writing) {
            handler.postDelayed(runnableCode, 500)
        } else {
            handler.removeCallbacks(runnableCode)
        }
    }

    fun startFocus(view: View) {
        writing=!writing
        (view as Button).text = if (writing) "STOP" else "START"
        startWriting()
    }

}