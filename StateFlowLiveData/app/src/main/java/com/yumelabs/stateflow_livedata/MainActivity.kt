package com.yumelabs.stateflow_livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yumelabs.stateflow_livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.launchWhenStarted{

        }
    }
}