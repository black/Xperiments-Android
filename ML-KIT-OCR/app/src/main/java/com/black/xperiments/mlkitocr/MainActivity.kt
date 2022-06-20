package com.black.xperiments.mlkitocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.black.xperiments.mlkitocr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /**
     * https://developers.google.com/ml-kit/vision/text-recognition/android
     */

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, OCRFragment())
            .commit()
    }
}