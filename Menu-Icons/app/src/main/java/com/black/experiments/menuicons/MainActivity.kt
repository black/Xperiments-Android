package com.black.experiments.menuicons

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.black.experiments.menuicons.databinding.ActivityMainBinding
import com.black.experiments.menuicons.models.SensorState
import com.black.experiments.menuicons.observers.FakeSensorViewModel
import com.black.experiments.menuicons.sensor.DataInterface
import com.black.experiments.menuicons.sensor.FakeSensor
import com.black.experiments.menuicons.sensor.PowerInterface
import com.black.experiments.menuicons.sensor.StatusInterface

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val fakeSensorViewModel: FakeSensorViewModel by viewModels()

    private var sensorOneStatus = "not_found"
    private var sensorTwoStatus = "not_found"
    private var onePowerProgressView: ProgressBar? = null
    private var twoPowerProgressView: ProgressBar? = null
    private var onePower = 0
    private var twoPower = 0
    private var onePowerPrev = 0
    private var twoPowerPrev = 0
    private val sensorOne = FakeSensor(10,1000)
    private val sensorTwo = FakeSensor(20,1000)

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
             R.id.action_start_two ->{
                 connectSensorTwo()
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

        /*----------EOG Connection---------------*/
        twoPowerProgressView =
            menu?.findItem(R.id.action_power_two)?.actionView?.findViewById(R.id.powerProgress) //two powerProgress
        twoPowerProgressView?.max = 20
        twoPowerProgressView?.progress = sensorModel.powerSensorTwo



        /* EOG Toolbar icon update */
        menu?.findItem(R.id.action_start_two)?.icon = ContextCompat.getDrawable(
            this,
            when(sensorModel.statusSensorTwo){
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
                fakeSensorViewModel.setFakeSensorOneConnectionStatus(status)
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
                fakeSensorViewModel.setFakeOneSignal(signal)
            }
        })
        sensorOne.setPowerListener(object : PowerInterface {
            override fun onData(power: Int) {
                fakeSensorViewModel.setFakeOnePower(power)
            }
        })
    }

    private fun connectSensorTwo(){
        sensorTwo.startConnection()
        sensorTwo.setConnectionStatusListener(object:StatusInterface{
            override fun onStatus(status: String) {
                fakeSensorViewModel.setFakeSensorTwoConnectionStatus(status)
                if(status=="connected"){
                    startSensorTwo()
                }
            }
        })
    }


    private fun startSensorTwo(){
        sensorTwo.startSensor()
        sensorTwo.setDataListener(object : DataInterface {
            override fun onData(signal: Int) {
                fakeSensorViewModel.setFakeTwoSignal(signal)
            }
        })
        sensorTwo.setPowerListener(object : PowerInterface {
            override fun onData(power: Int) {
                fakeSensorViewModel.setFakeTwoPower(power)
            }
        })
    }


    private fun observers(){
        fakeSensorViewModel.getFakeSensorOneConnectionStatus().observe(this){
            sensorOneStatus = it
            sensorModel.statusSensorOne = it
            invalidateOptionsMenu()
        }

        fakeSensorViewModel.getFakeOneSignal().observe(this){
            binding.contentMain.sensorValueOne.text = "$it"
        }

        fakeSensorViewModel.getFakeOnePower().observe(this){
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


        fakeSensorViewModel.getFakeSensorTwoConnectionStatus().observe(this){
            sensorTwoStatus = it
            sensorModel.statusSensorTwo = it
            invalidateOptionsMenu()
        }

        fakeSensorViewModel.getFakeTwoSignal().observe(this){
            binding.contentMain.sensorValueTwo.text = "$it"
        }

        fakeSensorViewModel.getFakeTwoPower().observe(this){
            twoPower = it
            sensorModel.powerSensorTwo = it
            binding.contentMain.powerValueTwo.text = "Second Power:  ${it}"

            if (twoPowerPrev!=twoPower) {
                twoPowerPrev = twoPower
                invalidateOptionsMenu()

            }

            if(it<1){
                sensorTwo.stopSensor()
            }
        }
    }
}