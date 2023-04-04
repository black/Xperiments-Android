package com.experiments.sirenviewpager

import android.content.DialogInterface
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.experiments.sirenviewpager.databinding.ActivityMainBinding
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val player = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun launchAlert(view: View) {
        customDialog()
    }

    private fun customDialog(){
        val builder = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view:View = factory.inflate(R.layout.dialogue_layout, null)
        val imgView = view.findViewById<ImageView>(R.id.fromGif)
        val button = view.findViewById<Button>(R.id.alarmButton)
        with(builder)
        {
            setView(view)
            setTitle(R.string.dialog_title)
            setMessage(R.string.dialog_message)
            loadGifImage(R.drawable.emergency,imgView)
            setCancelable(false)
            playAlertSound()
        }

        val alertDialog = builder.create();
        alertDialog.show()

        button.setOnClickListener {
            player.stop()
            alertDialog.dismiss()
        }
    }

    private fun loadGifImage(url:Int,view: ImageView){
        GlideApp.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(view)
    }

    private fun playAlertSound(){
        player.reset()
        val afd: AssetFileDescriptor?
        try {
            afd = assets.openFd("emergency.mp3")
            player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            player.isLooping = true
            player.prepare()
            player.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}