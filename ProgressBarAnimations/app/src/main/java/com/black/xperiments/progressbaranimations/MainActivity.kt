package com.black.xperiments.progressbaranimations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.black.xperiments.progressbaranimations.databinding.ActivityMainBinding
import java.lang.Math.random
import android.view.animation.LinearInterpolator

import android.animation.ObjectAnimator

import android.R
import android.view.View

import android.widget.ProgressBar




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /*Common variables*/
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun startProg(view: android.view.View) {
        val values = (random()*100).toInt()
        binding.testProgress.progress = values
        resetProgress(values)
    }

    private fun resetProgress(values:Int){
        ObjectAnimator.ofInt(binding.testProgress, "progress", values, 0).apply {
            duration = 1000L
            interpolator = LinearInterpolator()
            start()
        }
    }
}