package com.awearsense.gestureaccelerometer

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.awearsense.gestureaccelerometer.databinding.ActivityMainBinding
import uk.co.lemberg.motiondetectionlib.MotionDetector

class MainActivity : AppCompatActivity() {

    private val TAG = "Gestures ::: "

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var motionDetector: MotionDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        motionDetector = MotionDetector(this, gestureListener)
        motionDetector.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val gestureListener: MotionDetector.Listener = object : Listener() {
        fun onGestureRecognized(gestureType: MotionDetector.GestureType) {
            Log.d(TAG, "Gesture detected: $gestureType")
        }
    }
}