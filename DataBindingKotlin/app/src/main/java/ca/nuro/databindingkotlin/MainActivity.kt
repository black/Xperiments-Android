package ca.nuro.databindingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import ca.nuro.databindingkotlin.databinding.ActivityMainBinding
import java.lang.Math.random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var handler  = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fakeDataGenerator()
    }

    private fun fakeDataGenerator(){
        handler.postDelayed(runnable, 5000)
    }

    private val runnable: Runnable = Runnable {
        val fk = random()*100
        binding.dataOne = fk.toString()
        binding.dataTwo = fk.toInt().toString()
        Log.d("ScanData", "Data :${fk} ")
    }

}