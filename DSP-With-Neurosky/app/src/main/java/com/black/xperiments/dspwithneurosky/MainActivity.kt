package com.black.xperiments.dspwithneurosky

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.black.xperiments.dspwithneurosky.databinding.ActivityMainBinding
import com.black.xperiments.dspwithneurosky.extensions.GraphPlotters
import com.black.xperiments.dspwithneurosky.viewmodels.SignalViewModel
import com.boby.bluetoothconnect.LinkManager
import com.boby.bluetoothconnect.bean.BrainWave
import com.boby.bluetoothconnect.bean.Gravity
import com.boby.bluetoothconnect.classic.bean.BlueConnectDevice
import com.boby.bluetoothconnect.classic.listener.EEGPowerDataListener
import com.boby.bluetoothconnect.classic.listener.OnConnectListener
import com.github.psambit9791.jdsp.filter.Butterworth
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlin.math.sin


class MainActivity : AppCompatActivity() {
    private val TAG  = "DSP_EEG"
    private lateinit var binding: ActivityMainBinding
    private val signalViewModel: SignalViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    // Sensors status
    private var powerProgressView: ProgressBar? = null
    private var eeg_sensor = "not_found"

    // Graph Plotting
    private var seriesSignal = arrayListOf<LineGraphSeries<DataPoint>>()
    private var graph2LastSignalValue = arrayListOf<Double>()

    private var graphPlotters: GraphPlotters? = null

    // Signal Processing

    /*
     Define EEG bands
     eeg_bands = {
     'Delta': (1, 4),
     'Theta': (4, 8),
     'Alpha': (8, 12),
     'Beta': (12, 30),
     'Gamma': (30, 45)
     }
    */
    private var fs:Int = 512
    private var sampleSize = fs
    private var signal = arrayListOf<Double>()
    private var order:Int = 4
    private var lowCutOff:Int = 12 // Hz
    private var highCutOff:Int = 30  // Hz
    private var index  = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        validatePermission(permissions)

        try {
            LinkManager.init(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // serial data
        graphPlotters = GraphPlotters(this)
        val analysisList = resources.getStringArray(R.array.list_analysis)

//        analysisList.forEachIndexed { index, signalAnalysis ->
//            initGraphs(signalAnalysis, binding.rawView,index)
//        }

        for(i in 0..4){
            seriesSignal.add(LineGraphSeries<DataPoint>())
            graph2LastSignalValue.add(5.0)
        }

        initGraphs(resources.getString(R.string.raw), binding.rawView,0)
        initGraphs(resources.getString(R.string.filterSignal),binding.filteredView,1)
        initGraphs(resources.getString(R.string.fft),binding.fftView,2)
        initGraphs(resources.getString(R.string.psd), binding.psdView,3)
        initGraphs(resources.getString(R.string.bands),binding.bandsView,4)
    }

    private fun initGraphs(signalTitle: String, graphView:GraphView, pos:Int) {
        graphPlotters?.setSeries(seriesSignal[pos], R.color.brand, 1, R.color.brand)
        graphView.apply {
            addSeries(seriesSignal[pos])
            graphPlotters?.setGraph(this, 512, signalTitle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        powerProgressView =
            menu?.findItem(R.id.action_power)?.actionView?.findViewById(R.id.powerProgress) //powerProgress
        powerProgressView?.max = 100

        /* EEG Toolbar icon update */
        when (eeg_sensor) {
            "started" -> menu?.findItem(R.id.action_eeg)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_action_connect_idle
            )
            "connecting" -> menu?.findItem(R.id.action_eeg)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_action_connect_idle
            )
            "failed" -> menu?.findItem(R.id.action_eeg)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_action_eeg_disconnected
            )
            "connected" -> {
                menu?.findItem(R.id.action_eeg)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_eeg_connected
                )
            }
            "disconnected" -> menu?.findItem(R.id.action_eeg)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_action_eeg_disconnected
            )
            else -> menu?.findItem(R.id.action_eeg)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_action_eeg_not_found
            )
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_eeg -> {
                connectSensor()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        observers()
    }

    private fun observers() {
        signalViewModel.getConnection().observe(this) {
            eeg_sensor = it
            invalidateOptionsMenu()
        }

        signalViewModel.getPower().observe(this) {
            powerProgressView?.progress = it
        }

        signalViewModel.getRaw().observe(this) {
            seriesSignal[0].appendData(
                DataPoint(graph2LastSignalValue[0], it.toDouble()),
                true,
                512
            )
            graph2LastSignalValue[0] += 1.0

            if(signal.size<sampleSize){
                signal.add(it.toDouble())
            }else{
                getFilteredSignal(signal)
                signal.removeAll{it<signal.size/2}
            }
        }
    }

    val Boolean.int
        get() = if (this) 1 else 0


    private fun applyFilter(signal:DoubleArray):DoubleArray{
        val filter = Butterworth(signal,fs.toDouble())
        return filter.bandPassFilter(order, lowCutOff.toDouble(),highCutOff.toDouble())
    }

    private fun getFFT(){
    }

    private fun analysed(sample:DoubleArray): Array<DataPoint?> {
        val count = sample.size
        val values = arrayOfNulls<DataPoint>(count)
        for (i in 0 until count) {
            val x = i.toDouble()
            val y: Double = sample[i]
            val v = DataPoint(x, y)
            values[i] = v
        }
        return values
    }

    private fun getFilteredSignal(sample:ArrayList<Double>){
        val transformedSample = sample.toDoubleArray()
        seriesSignal[1].resetData(analysed(applyFilter(transformedSample)))
    }

    private fun getBands(sample:DoubleArray){

    }



    /*---- EEG Sensor -------*/
    private var eegListener: EEGPowerDataListener = object : EEGPowerDataListener {
        override fun onBrainWavedata(s: String?, brainWave: BrainWave?) {
            handler.post {
                brainWave?.let { power ->
                    val bands = IntArray(8)
                    bands[0] = power.delta
                    bands[1] = power.theta
                    bands[2] = power.lowAlpha
                    bands[3] = power.highAlpha
                    bands[4] = power.lowBeta
                    bands[5] = power.highBeta
                    bands[6] = power.lowGamma
                    bands[7] = power.middleGamma

                    signalViewModel.setBands(bands)
                    signalViewModel.setSignalStrength(power.signal)
                    signalViewModel.setFocus(power.att)
                    signalViewModel.setCalm(power.med)
                    signalViewModel.setAppreciation(power.ap)
                    signalViewModel.setPower(power.batteryCapacity)
                    signalViewModel.setMentalEffort(0)
                    signalViewModel.setFamiliarity(0)
                }
            }
        }

        override fun onRawData(s: String?, i: Int) {
            handler.post {
                signalViewModel.setRaw(i)
            }
        }

        override fun onGravity(s: String?, gravity: Gravity?) {
            handler.post {
                gravity?.let {
                    val motion = IntArray(3)
                    motion[0] = it.X //偏航角度值
                    motion[1] = it.Y //俯仰角度值
                    motion[2] = it.Z //横滚角度值
                }
            }
        }
    }

    private var onConnectListener: OnConnectListener = object : OnConnectListener {
        override fun onError(e: java.lang.Exception?) {
            handler.post {
                signalViewModel.setConnection("error $e")
            }
        }

        override fun onConnectionLost(blueConnectDevice: BlueConnectDevice?) {
            handler.post {
                signalViewModel.setConnection("disconnected")
            }
        }

        override fun onConnectStart(blueConnectDevice: BlueConnectDevice?) {
            handler.post {
                signalViewModel.setConnection("started")
            }
        }

        override fun onConnectting(blueConnectDevice: BlueConnectDevice?) {
            handler.post {
                signalViewModel.setConnection("connecting")
            }
        }

        override fun onConnectFailed(blueConnectDevice: BlueConnectDevice?) {
            handler.post {
                signalViewModel.setConnection("failed")
            }
        }

        override fun onConnectSuccess(blueConnectDevice: BlueConnectDevice?) {
            handler.post {
                signalViewModel.setConnection("connected")
            }
        }

    }

    private fun connectSensor() {
        LinkManager.getInstance().setEegPowerDataListener(eegListener)
        LinkManager.getInstance().setOnConnectListener(onConnectListener)
        LinkManager.getInstance().whiteList = "BrainLink_Pro,NURO"
        LinkManager.getInstance().startScan()
    }

    fun disConnectSensor(view: View?) {
        LinkManager.getInstance().close()
    }

    private fun validatePermission(permissions: List<String>) {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.storage_permission_rationale_title)
                            .setMessage(R.string.storage_permission_rationale_message)
                            .setNegativeButton(
                                android.R.string.cancel,
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    p1?.cancelPermissionRequest()
                                })
                            .setPositiveButton(
                                android.R.string.ok,
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    p1?.continuePermissionRequest()
                                })
                            .show()
                    }
                }
            ).check()
    }
}