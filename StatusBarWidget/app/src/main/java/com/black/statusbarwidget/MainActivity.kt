package com.black.statusbarwidget

import android.graphics.*
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.black.statusbarwidget.NotificationWidget.Companion.CHANNEL_1_ID
import com.black.statusbarwidget.NotificationWidget.Companion.CHANNEL_2_ID
import com.black.statusbarwidget.NotificationWidget.Companion.CHANNEL_3_ID
import com.black.statusbarwidget.viewmodels.SignalViewModel
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


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
            val merakiNotification = NotificationCompat.Builder(this,CHANNEL_1_ID).apply {
                setContentTitle("Meraki ${it}")
                val icon = resources.getIdentifier("ic_power_$it", "drawable", packageName)
                setSmallIcon(icon)
                priority = NotificationCompat.PRIORITY_HIGH
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
            val tonokaNotification = NotificationCompat.Builder(this,CHANNEL_2_ID).apply {
                setContentTitle("Tonoka ${it}")
                setSmallIcon(R.drawable.animation)
                priority = NotificationCompat.PRIORITY_HIGH
            }

            NotificationManagerCompat.from(this).apply {
                notify(2, tonokaNotification.build())
            }

            /*Log.d("NotificationValue","Tonoka ${it}")
            val tonokaNotification = NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_accessibility_24)
                .setContentTitle("Tonoka ${it}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            notificationManager?.notify(1,tonokaNotification)*/
        })
        signalViewModel.getGorku().observe(this,{
            val gorkuNotification = NotificationCompat.Builder(this,CHANNEL_3_ID).apply {
                setContentTitle("Goruku ${it}")
                if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                   // setSmallIcon(Icon.createWithBitmap(getBitmapFromURL("https://www.codeproject.com/script/Membership/ProfileImages/%7B37272a74-1775-4f37-97f5-33615360b397%7D.jpg")))
                }
                priority = NotificationCompat.PRIORITY_HIGH
            }

            NotificationManagerCompat.from(this).apply {
                notify(3, gorkuNotification.build())
            }
        })
    }

   /* private fun getBitmapFromURL(strURL: String?): Bitmap? {
        return try {
            val url = URL(strURL)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }*/


    private var i: Int = 0
    private val runnableCode = object: Runnable {
        override fun run() {
            if(i<3)i += 1
            else i=0
            signalViewModel.setMeraki(i)
            signalViewModel.setTonoka(i )
            signalViewModel.setGorku(i)
            handler.postDelayed(this, 200)
        }
    }
    private fun startWriting() {
        if (writing) {
            handler.postDelayed(runnableCode, 200)
        } else {
            handler.removeCallbacks(runnableCode)
        }
    }

    fun startFocus(view: View) {
        writing=!writing
        (view as Button).text = if (writing) "STOP" else "START"
        startWriting()
    }

   /* private fun generateNotification(message: String) {
        val icon: Int = R.drawable.ic_status
        val mytime = System.currentTimeMillis()

        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = Notification(icon, message, mytime)
        val title = applicationContext.getString(R.string.app_name) // Here you can pass the value of your TextView
        val notificationIntent = Intent(applicationContext, SplashScreen::class.java)
        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val intent = PendingIntent.getActivity(
            applicationContext, 0,
            notificationIntent, 0
        )
        notification.setLatestEventInfo(applicationContext, title, message, intent)
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        notificationManager.notify(0, notification)
    }*/

}