package com.black.xperiments.kalman_filter_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.black.xperiments.kalman_filter_kotlin.databinding.ActivityMainBinding
import java.lang.Throwable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KalmanRx.createFrom1D(floatObservable.map { e -> e.value })
            .subscribe({ value -> }, Throwable::printStackTrace)
    }
}