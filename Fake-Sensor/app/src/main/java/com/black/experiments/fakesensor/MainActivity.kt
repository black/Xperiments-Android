package com.black.experiments.fakesensor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.black.experiments.fakesensor.databinding.ActivityMainBinding
import com.black.experiments.fakesensor.models.SensorState
import com.black.experiments.fakesensor.observers.FakeSensorViewModel
import com.black.experiments.fakesensor.sensor.DataInterface
import com.black.experiments.fakesensor.sensor.FakeSensor
import com.black.experiments.fakesensor.sensor.PowerInterface
import com.black.experiments.fakesensor.sensor.StatusInterface

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val fakeSensorViewModel: FakeSensorViewModel by viewModels()

    private var sensorOneStatus = "not_found"
    private var onePowerProgressView: ProgressBar? = null
    private var onePower = 0
    private var onePowerPrev = 0
    private val sensorOne = FakeSensor(10,1000)

    private var sensorModel = SensorState()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        observers()
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
         when (item.itemId) {
            R.id.action_start_one -> {
                connectSensorOne()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        /*----------EOG Connection---------------*/
        onePowerProgressView =
            menu?.findItem(R.id.action_power_one)?.actionView?.findViewById(R.id.powerProgress) //one powerProgress
        onePowerProgressView?.max = 10
        onePowerProgressView?.progress = sensorModel.powerSensorOne

        /* EOG Toolbar icon update */
       menu?.findItem(R.id.action_start_one)?.icon = ContextCompat.getDrawable(
            this,
            when(sensorModel.statusSensorOne){
                "started"->R.drawable.ic_action_started
                "connecting"->R.drawable.ic_action_connecting
                "connected"->R.drawable.ic_action_connected
                "disconnected"-> R.drawable.ic_action_disconnected
                 else-> R.drawable.ic_action_not_found
            }
        )
        return super.onPrepareOptionsMenu(menu)
    }

    private fun connectSensorOne(){
        sensorOne.startConnection()
        sensorOne.setConnectionStatusListener(object:StatusInterface{
            override fun onStatus(status: String) {
                fakeSensorViewModel.fakeConnectionOne.value = status
                if(status=="connected"){
                    startSensorOne()
                }
            }

        })
    }

    private fun startSensorOne(){
        sensorOne.startSensor()
        sensorOne.setDataListener(object : DataInterface {
            override fun onData(signal: Int) {
                fakeSensorViewModel.fakeSignalOne.value = signal
            }
        })
        sensorOne.setPowerListener(object : PowerInterface {
            override fun onData(power: Int) {
                fakeSensorViewModel.fakePowerOne.value = power
            }
        })
    }


    private fun observers(){
        fakeSensorViewModel.fakeConnectionOne.observe(this){
            sensorOneStatus = it
            sensorModel.statusSensorOne = it
            invalidateOptionsMenu()
        }

        fakeSensorViewModel.fakeSignalOne.observe(this){
            binding.contentMain.sensorValueOne.text = "$it"
        }

        fakeSensorViewModel.fakePowerOne.observe(this){
            onePower = it
            sensorModel.powerSensorOne = it
            binding.contentMain.powerValueOne.text = "First Power:  ${it}"

            if (onePowerPrev!=onePower) {
                onePowerPrev = onePower
                invalidateOptionsMenu()
            }

            if(it<1){
                sensorOne.stopSensor()
            }
        }
    }
}