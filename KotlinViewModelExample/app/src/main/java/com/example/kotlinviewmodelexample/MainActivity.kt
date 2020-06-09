package com.example.kotlinviewmodelexample

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import android.widget.TextView
import java.util.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var mModel:DummyViewModel
    lateinit var randValue:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randValue = findViewById(R.id.randValue) as TextView
        // Get the view model
        mModel = ViewModelProviders.of(this).get(DummyViewModel::class.java)
    }

    fun generateRand(view: View?){
        Log.d("TAG","CLICKED");
        var ggggg =  Random().nextInt(50)
        mModel.setRandomNumber.value = ggggg
        randValue.text = "RAND: $ggggg"
    }

    fun openFragmentOne(view: View?){
        Log.d("TAG","Fragment One");
        replaceFragment(FirstFragment())
    }

    fun openFragmentTwo(view: View?){
        Log.d("TAG","Fragment Two");
        replaceFragment(SecondFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragview, fragment)
        transaction.commit()
    }
}
