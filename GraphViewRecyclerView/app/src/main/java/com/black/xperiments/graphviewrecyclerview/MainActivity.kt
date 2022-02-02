package com.black.xperiments.graphviewrecyclerview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.graphviewrecyclerview.databinding.ActivityMainBinding
import com.black.xperiments.graphviewrecyclerview.fakesensor.SensorSim
import com.black.xperiments.graphviewrecyclerview.fakesensor.SensorStream
import com.black.xperiments.graphviewrecyclerview.graphs.OnItemClickListener
import com.black.xperiments.graphviewrecyclerview.graphs.RecyclerViewGraphViewAdapter
import com.black.xperiments.graphviewrecyclerview.models.FusionStateData
import com.black.xperiments.graphviewrecyclerview.models.GraphObject
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class MainActivity : AppCompatActivity(), SensorStream {

    private val TAG = "SignalFusion"
    private lateinit var binding: ActivityMainBinding

    private var sensorSim: SensorSim?=null
    private val someViewModel: SomeViewModel by viewModels()
    private var fusionStateData = FusionStateData()
    private var started = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sensorSim = SensorSim(this)
        observers()
    }

    fun startSensor(view: View) {
        started = !started
        sensorSim?.startSensor()
        sensorSim?.setSensorStream(this)
        view.setBackgroundColor(if(started) Color.GREEN else Color.BLACK)
    }

    fun startFragment(view: View) {
        val bottomFragment = BottomFragment.newInstance(someViewModel,fusionStateData)
        bottomFragment.show(supportFragmentManager, "Fusion")
    }

    private fun observers(){
        someViewModel.getFusionState().observe(this){
            fusionStateData = it
            for (i in 0..3){
                Log.d(TAG,"${fusionStateData.state[i]} ${fusionStateData.threshold[i][0]} ${fusionStateData.threshold[i][1]}")
            }
        }
    }

    override fun onData(signal: ArrayList<Double>) {
        someViewModel.setSensorData(signal)
    }

}