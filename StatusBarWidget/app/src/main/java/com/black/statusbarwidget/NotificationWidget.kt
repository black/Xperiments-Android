package com.black.statusbarwidget

import android.app.Application
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.app.NotificationManager as NotificationManager

class NotificationWidget: Application() {
    companion object {
        var CHANNEL_1_ID = "Meraki"
        var CHANNEL_2_ID = "Tonoka"
    }
    private var notificationManager: NotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val merakiChannel = NotificationChannel(CHANNEL_1_ID,"MERAKI", NotificationManager.IMPORTANCE_LOW)
            merakiChannel.description = "MERAKI"

            val tonokaChannel = NotificationChannel(CHANNEL_2_ID,"TONOKA", NotificationManager.IMPORTANCE_LOW)
            tonokaChannel.description = "TONOKA"

            notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(merakiChannel)
            notificationManager?.createNotificationChannel(tonokaChannel)
        }
    }
}