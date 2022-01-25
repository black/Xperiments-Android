package com.black.xperiments.custom_sdk_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.black.xperiments.custom_sdk_library.databinding.ActivityMainBinding
import com.black.xperiments.my_new_library.MyLibrary

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var myLib: MyLibrary?=null
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myLib = MyLibrary()
    }

    fun actionOne(view: android.view.View) {
        myLib?.see("Hello World")
    }

    fun actionTwo(view: android.view.View) {
        count = if(count>0) 0 else 1
        var msgb = myLib?.blind(count)
        Log.d("MY_CUSTOM_LIBRARY",msgb!!)
    }


}