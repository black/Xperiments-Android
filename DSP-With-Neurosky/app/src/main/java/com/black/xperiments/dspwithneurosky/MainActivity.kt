package com.black.xperiments.dspwithneurosky

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
    private var signal = doubleArrayOf()
    private var fs:Int = 512
    private var sampleSize = fs
    private var order:Int = 4
    private var lowCutOff:Int = 2 // Hz
    private var highCutOff:Int = 50 // Hz
    private var filter:Butterworth?=null
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
        graphPlotters = GraphPlotters(this)
        try {
            LinkManager.init(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        for(i in 0..3){
            seriesSignal.add(LineGraphSeries<DataPoint>())
            graph2LastSignalValue.add(5.0)
        }

        initGraphs(resources.getString(R.string.raw), binding.rawView,0)
        initGraphs(resources.getString(R.string.fft),binding.fftView,1)
        initGraphs(resources.getString(R.string.psd), binding.psdView,2)
        initGraphs(resources.getString(R.string.bands),binding.bandsView,3)

        filter = Butterworth(signal,fs.toDouble())

    }

    // LayoutSignalGraphBinding

    private fun initGraphs(signalTitle: String, graphView:GraphView ,pos:Int) {
        graphPlotters?.setSeries(seriesSignal[pos], R.color.brand, 3, R.color.brand)
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
            if(index<sampleSize) {
                // signal[index] = it.toDouble()
                val resultSignal  = filter?.bandPassFilter(order, lowCutOff.toDouble(),highCutOff.toDouble())
                Log.d(TAG,"$resultSignal")
                // https://jdsp.dev/filters.html
                // seriesSignal[1].appendData(DataPoint(graph2LastSignalValue[0],resultSignal),true,512)
                index++
            }else index = 0

            graph2LastSignalValue[0] += 1.0
        }
    }

    val Boolean.int
        get() = if (this) 1 else 0


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