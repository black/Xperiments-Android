package com.awearsense.graphviewbarcharts

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.awearsense.graphviewbarcharts.Constants.BUFFERSIZE
import com.awearsense.graphviewbarcharts.Constants.TAG
import com.awearsense.graphviewbarcharts.databinding.ActivityMainBinding
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class MainActivity : AppCompatActivity(),SensorEventListener {

    private val TAG = "FAKE SENSOR"
    private lateinit var binding: ActivityMainBinding
    private val dataViewModel: DataViewModel by viewModels()

    //----- signal processing ----

    private var oldFFT = DoubleArray(60)

    //----sensor---------
    private var sensor:Sensor?=null
    private var sensorManager:SensorManager?=null
    private var x = 0
    private var y = 0
    private var z = 0
    private var lastUpdate = 0

    //----graphs---------
    private var graphPlotters =  GraphPlotters()
    private var seriesRaw = LineGraphSeries<DataPoint>()
    private var seriesSample = LineGraphSeries<DataPoint>()
    private var seriesFFT = LineGraphSeries<DataPoint>()
    private var graphDataStep = 5.0



    private var realtimeFFT:RealtimeFFT?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        realtimeFFT = RealtimeFFT()

        initSensor()
        initGraphs()
        observers()
    }

    private fun initSensor(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    private fun initGraphs(){
        graphPlotters.apply{
            setSeries(applicationContext, seriesRaw, R.color.black, 2)
            setSeries(applicationContext, seriesSample,R.color.black,2)
            setSeries(applicationContext, seriesFFT, R.color.black, 2)
        }

        binding.apply {
            dataView.addSeries(seriesRaw)
            barChart.addSeries(seriesSample)
            fftView.addSeries(seriesFFT)

            graphPlotters.apply {
                setGraph(dataView, BUFFERSIZE,"Raw Signal")
                setGraph(barChart,BUFFERSIZE,"Bands Signal")
                setGraph(fftView,60,"FFT Signal")
            }
        }
    }

    private fun observers(){
        dataViewModel.x.observe(this) {
            graphDataStep += 1.0
            seriesRaw.appendData(DataPoint(graphDataStep, it.toDouble()), true, BUFFERSIZE)
            dataViewModel.fft.value = realtimeFFT?.processSample(it.toDouble())
        }

        dataViewModel.segment.observe(this) {
            Log.d(TAG,"${it.size}")
            seriesSample.resetData(emptyArray())
            seriesSample.resetData(convertToDataPoints(it))

            // perform FFT on real time signal
        }

        dataViewModel.fft.observe(this) {
            val temp = addDoubleArrays(it.take(60).toDoubleArray(),oldFFT)
            seriesFFT.resetData(emptyArray())
            seriesFFT.resetData(convertToDataPoints(temp))
        }
    }

    private fun addDoubleArrays(array1: DoubleArray, array2: DoubleArray): DoubleArray {
        require(array1.size == array2.size) { "Arrays must have the same size" }
        return array1.zip(array2) { a, b -> a + b*0.7 }.toDoubleArray()
    }


    // New separate function to convert Double array to DataPoint array
    private fun convertToDataPoints(dataArray: DoubleArray): Array<DataPoint> {
        return dataArray.mapIndexed { index, value ->
            DataPoint(index.toDouble(), value)
        }.toTypedArray()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val xx = values[0].toInt()
                val yy = values[1].toInt()
                val zz = values[2].toInt()

                dataViewModel.x.value = xx
                dataViewModel.y.value = yy
                dataViewModel.z.value = zz

//                val currentTime = System.currentTimeMillis()
//                if (currentTime - lastUpdate > 100) {
//                    var timeDiff = currentTime - lastUpdate
//                    lastUpdate = currentTime.toInt()
//                    var speed = Math.abs(xx + yy + zz - x - y - z) / timeDiff * 10000
//                    if(speed>t)
//                }

            }

        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }
}