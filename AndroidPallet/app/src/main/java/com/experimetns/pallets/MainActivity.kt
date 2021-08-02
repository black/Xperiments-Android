package com.experimetns.pallets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import co.revely.gradient.RevelyGradient
import com.experimetns.pallets.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.song_tile)
        setPalette(bitmap)
    }

//    private fun setPallete(albumArt: String?) {
//        seekBar?.max = MusicPlayer.getPlayingCUPart()?.durationS ?: 0
//        seekBar?.progress = MusicPlayer.getPlayingCUPart()?.seekPosition ?: 0
//        bg {
//            Palette.from(ImageManager.getBitmapSync(albumArt.toString())).generate {
//                setBgColor(
//                    it?.getDominantColor(0x002171) ?: 0x002171,
//                    it?.getLightMutedColor(0x002171) ?: 0x002171,
//                    it,
//                    main_content,
//                    90.0F
//                )
//                setBgColor(
//                    it?.getDominantColor(0x002171) ?: 0x002171,
//                    it?.getLightMutedColor(0x002171) ?: 0x002171,
//                    it,
//                    activity_audio_bg_pallete,
//                    90.0F
//                )
//            }
//        }
//    }

    private fun setPalette(bitmap:Bitmap){
        Palette.from(bitmap).generate {
            setBgColor(
                it?.getDominantColor(0x002171) ?: 0x002171,
                it?.getLightMutedColor(0x002171) ?: 0x002171,
                it,
                binding.root,
                90.0F
            )
        }
    }

    private fun setBgColor(
        color: Int,
        lightVibrantColor: Int? = null,
        palette: Palette? = null,
        view: View,
        angle: Float
    ) {
        RevelyGradient
            .linear()
            .colors(
                intArrayOf(
                    color,
                    Color.parseColor("#212121"),
                    Color.parseColor("#151515")
                )
            )
            .angle(angle)
            .offsets(floatArrayOf(0.0f, 0.85f,1.0f))
            .onBackgroundOf(view)
    }
}