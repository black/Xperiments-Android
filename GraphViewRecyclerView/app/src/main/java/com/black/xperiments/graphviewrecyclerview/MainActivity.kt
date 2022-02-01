package com.black.xperiments.graphviewrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.graphviewrecyclerview.databinding.ActivityMainBinding
import com.black.xperiments.graphviewrecyclerview.fakesensor.SensorSim
import com.black.xperiments.graphviewrecyclerview.fakesensor.SensorStream
import com.black.xperiments.graphviewrecyclerview.graphs.OnItemClickListener
import com.black.xperiments.graphviewrecyclerview.graphs.RecyclerViewGraphViewAdapter
import com.black.xperiments.graphviewrecyclerview.models.FusionData
import com.black.xperiments.graphviewrecyclerview.models.GraphObject
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class MainActivity : AppCompatActivity(), SensorStream {

    private val TAG = "GraphViewRecyclerView"
    private lateinit var binding: ActivityMainBinding
    private var rvGraphViewAdapter: RecyclerViewGraphViewAdapter?=null
    private var graphViewDataList  = arrayListOf<GraphObject>()
    private var sensorSim: SensorSim?=null

    private var graphALastEOG = 5.0
    private var graphBLastEOG = 5.0
    private var graphCLastEOG = 5.0
    private var graphDLastEOG = 5.0
    private var graphELastEOG = 5.0
    private var thresholds = arrayListOf<FusionData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvGraphViewAdapter = RecyclerViewGraphViewAdapter(this,graphViewDataList)
        binding.graphViewList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = rvGraphViewAdapter
        }

        rvGraphViewAdapter?.setOnItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(TAG,"$position")
            }

            override fun onSwitchChange(position: Int, state: Boolean) {
                Log.d(TAG,"$position $state")
                graphViewDataList[position].state = state
            }

            override fun onRangeSliderChange(position: Int, lower: Float, upper: Float) {
                thresholds[position] = FusionData(lower.toDouble(), upper.toDouble())
            }

        })
        sensorSim = SensorSim(this)
        sensorSim?.starSensor()
        sensorSim?.setSensorStream(this)
        createListItems()
    }

    private fun createListItems(){
        for(i in 0..5) {
            val seriesSignal = ArrayList<LineGraphSeries<DataPoint>>()

            val signal = LineGraphSeries<DataPoint>()
            val upperThreshold = LineGraphSeries<DataPoint>()
            val lowerThreshold = LineGraphSeries<DataPoint>()

            seriesSignal.add(signal)
            seriesSignal.add(lowerThreshold)
            seriesSignal.add(upperThreshold)
            thresholds.add(FusionData(0.0,0.0))
            graphViewDataList.add(GraphObject("signal $i",false,seriesSignal))
        }
    }

    override fun dataA(signal: Double) {
//        rvGraphViewAdapter?.updateSeries(0,signal,graphALastEOG)
//        graphALastEOG += 1.0
    }

    override fun dataB(signal: Double) {
//        rvGraphViewAdapter?.updateSeries(1,signal,graphBLastEOG)
//        graphBLastEOG += 1.0
    }

    override fun dataC(signal: Double) {
//        rvGraphViewAdapter?.updateSeries(2,signal,graphCLastEOG)
//        graphCLastEOG += 1.0
    }

    override fun dataD(signal: Double) {
//        rvGraphViewAdapter?.updateSeries(3,signal,graphDLastEOG)
//        graphDLastEOG += 1.0
    }

    override fun dataE(signal: Double) {
//        rvGraphViewAdapter?.updateSeries(4,signal,graphELastEOG)
//        graphELastEOG += 1.0
    }

}