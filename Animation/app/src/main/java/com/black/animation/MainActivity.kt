package com.black.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.black.animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlideApp.with(this)
            .load("https://www.freepnglogos.com/uploads/google-logo-png/google-logo-icon-png-transparent-background-osteopathy-16.png")
            .placeholder(R.drawable.test_animation)
            .error(R.drawable.ic_broken_image_black_24dp)
            .into(binding.animationView)
    }
}