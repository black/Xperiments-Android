package com.black.xperiments.headtracking

import android.Manifest
import android.app.Dialog
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.black.xperiments.headtracking.databinding.ActivityMainBinding
import com.jins_jp.meme.MemeConnectListener
import com.jins_jp.meme.MemeLib
import com.jins_jp.meme.MemeRealtimeListener
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.ArrayList
import com.black.xperiments.headtracking.direction.EyeMovementDirection


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val sensorViewModel: SensorViewModel by viewModels()
    private val TAG = "SENSORVAL"
    /*Common variables*/
    private val handler = Handler(Looper.getMainLooper())
    private var memeLib: MemeLib? = null
    private var deviceListView: ListView? = null
    private val deviceList = ArrayList<String>()
    private var scannedAddressAdapter: ArrayAdapter<String>? = null
    private var eogDialog: Dialog? = null
    private var eogSensor = "not_found"
    private var eogPowerPrev = 0
    private var eogPowerProgressView: ProgressBar? = null

    /* Plotting*/
    private var seriesAccX = LineGraphSeries<DataPoint>()
    private var seriesAccY = LineGraphSeries<DataPoint>()
    private var seriesAccZ = LineGraphSeries<DataPoint>()
    private var seriesPitch = LineGraphSeries<DataPoint>()
    private var seriesYaw = LineGraphSeries<DataPoint>()
    private var seriesRoll = LineGraphSeries<DataPoint>()

    private var graphLastXValue = 5.0

    private var eyeMovementDirection: EyeMovementDirection?=null
    private var eyeMove = "center"
    private var leftCount = 0
    private var rightCount = 0
    private var prevDir = 0
    private var pitchPrev = 0
    private var yawPrev = 0
    private var rollPrev = 0
    private var someCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        validatePermission(permissions)

        /*---------- EOG Code-----------------*/
        eogDialog = Dialog(this)
        eogDialog!!.setContentView(R.layout.dialog_eog)
        deviceListView = eogDialog!!.findViewById(R.id.deviceListView)
        scannedAddressAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList)
        deviceListView!!.adapter = scannedAddressAdapter

        if (savedInstanceState == null) {
            eogInit()
        }
        initGraphs()
        observers()
    }

    private fun initGraphs(){
        setSeries(seriesAccX, R.color.oneL, 3, R.color.one)
        setSeries(seriesAccY, R.color.one, 3, R.color.one)
        setSeries(seriesAccZ, R.color.twoL, 3, R.color.two)
        setSeries(seriesPitch, R.color.two, 3, R.color.two)
        setSeries(seriesYaw, R.color.threeL, 3, R.color.threeL)
        setSeries(seriesRoll, R.color.three, 3, R.color.three)

        binding.root.post {
            binding.accXPlot.addSeries(seriesAccX)
            binding.accYPlot.addSeries(seriesAccY)
            binding.accZPlot.addSeries(seriesAccZ)
            binding.pitchPlot.addSeries(seriesPitch)
            binding.yawPlot.addSeries(seriesYaw)
            binding.rollPlot.addSeries(seriesRoll)

            setGraph(binding.accXPlot, 100, "Acc X")
            setGraph(binding.accYPlot, 100, "Acc y")
            setGraph(binding.accZPlot, 100, "Acc Z")
            setGraph(binding.pitchPlot, 100, "Pitch")
            setGraph(binding.yawPlot, 100, "Yaw")
            setGraph(binding.rollPlot, 100, "Roll")
        }
    }

    private fun observers(){

        /* EOG */
        sensorViewModel.getEOGConnect().observe(this, {
            eogSensor = it
            invalidateOptionsMenu()
        })

        sensorViewModel.getEOGPower().observe(this, {
            eogPowerProgressView?.progress = it
        })

        sensorViewModel.getEOGAllData().observe(this,{
            Log.d(TAG,"ACC-> aclX ${it.accX } aclY ${it.accY} aclZ ${it.accZ} GYRO -> P:${it.pitch} Y:${it.yaw} R:${it.roll}")
            seriesAccX.appendData(DataPoint(graphLastXValue, it.accX.toDouble()), true, 100)
            seriesAccY.appendData(DataPoint(graphLastXValue, it.accY.toDouble()), true, 100)
            seriesAccZ.appendData(DataPoint(graphLastXValue, it.accZ.toDouble()), true, 100)
            seriesPitch.appendData(DataPoint(graphLastXValue, it.pitch.toDouble()), true, 100)
            seriesYaw.appendData(DataPoint(graphLastXValue, it.yaw.toDouble()), true, 100)
            seriesRoll.appendData(DataPoint(graphLastXValue, it.roll.toDouble()), true, 100)
            graphLastXValue += 1.0
            eyeMovementDirection = EyeMovementDirection(it)

            if(it.eyeMoveLeft>0 || it.eyeMoveRight>0){
                someCount = it.eyeMoveLeft-it.eyeMoveRight
            }
             binding.eyeMoveDirection.text =  "${someCount}  ${ if(someCount>0) "right" else "left"}"


//            if(it.eyeMoveRight==0 && it.eyeMoveLeft==0){
//                binding.eyeMoveDirection.text =  eyeMove+"but it is"+ if(eyeMove=="right") "left" else "right"
//            }

            //  val dir = eyeMovementDirection?.getHorizontalDirection()

//            binding.eyeMoveDirection.text =  when(it){
//                (it.eyeMoveLeft?>0) -> {
//                    "Left"
//                }
//                (it.eyeMoveRight?>0)-> {
//                    "Right"
//                }
//                else->{
//                     "center"
//                }
//            }
        })
    }

    private fun checkIfThis(value:Int,threshold:Int):Boolean{
        return value>threshold
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        eogPowerProgressView =
            menu?.findItem(R.id.action_power_eog)?.actionView?.findViewById(R.id.powerProgress) //eog powerProgress
        eogPowerProgressView?.max = 5

        /* EOG Toolbar icon update */
        when (eogSensor) {
            "not_found" -> {
                menu?.findItem(R.id.action_eog)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_eog_not_found
                )
            }
            "connecting" -> {
                menu?.findItem(R.id.action_eog)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_idle
                )
            }
            "connected" -> {
                menu?.findItem(R.id.action_eog)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_eog_connected
                )
            }
            "disconnected" -> {
                menu?.findItem(R.id.action_eog)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_eog_disconnected
                )
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_eog -> {
                startEOG()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*--------------------------*/
    private fun eogInit() {
        MemeLib.setAppClientID(this, "293295661668136", "u01hk6juf8orh6nm6kfcc256uqqbey91")
        memeLib = MemeLib.getInstance() //MemeLib is singleton
        memeLib!!.setVerbose(true)
        deviceListView!!.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, i, _ ->
                vibrate()
                val address = deviceList[i]
                memeLib!!.connect(address)
                memeLib!!.setMemeConnectListener(object : MemeConnectListener {
                    override fun memeConnectCallback(b: Boolean) {
                        runOnUiThread {
                            memeLib!!.startDataReport(memeRealtimeListener)
                            sensorViewModel.setEOGConnect("connected")
                            eogDialog?.dismiss()
                            vibrate()
                        }
                    }
                    override fun memeDisconnectCallback() {
                        runOnUiThread { sensorViewModel.setEOGConnect("disconnected") }
                    }
                })
            }
    }

    private fun startEOG() {
        eogDialog?.show()
        clearList()
        handler.postDelayed({
            val status = memeLib!!.startScan { address ->
                runOnUiThread {
                    deviceList.add(address)
                    scannedAddressAdapter!!.notifyDataSetChanged()
                }
            }
        }, 200L)
        sensorViewModel.setEOGConnect("connecting")
    }

    private fun clearList() {
        scannedAddressAdapter!!.clear()
        deviceListView!!.deferNotifyDataSetChanged()
    }

    fun stopScan(view: View) {
        eogDialog?.dismiss()
        if (memeLib!!.isScanning) memeLib!!.stopScan()
    }


    var memeRealtimeListener = MemeRealtimeListener {
        runOnUiThread {
            val eogPower = it.powerLeft
            if (eogPower != eogPowerPrev) {
                eogPowerPrev = eogPower
                sensorViewModel.setEOGPower(eogPower)
            }
            sensorViewModel.setEOGAllData(it)
        }
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
                                android.R.string.cancel
                            ) { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.cancelPermissionRequest()
                            }
                            .setPositiveButton(
                                android.R.string.ok
                            ) { dialogInterface, _ ->
                                dialogInterface.dismiss()
                                p1?.continuePermissionRequest()
                            }
                            .show()
                    }
                }
            ).check()
    }

    fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(320L)
    }

    private fun setSeries(
        series: LineGraphSeries<DataPoint>,
        color: Int,
        thickness: Int,
        background: Int
    ) {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = thickness.toFloat()
        paint.color = ContextCompat.getColor(this, color)
        series.setCustomPaint(paint)
        series.color = ContextCompat.getColor(this, color)
        series.thickness = thickness
//
    }

    private fun setGraph(graph: GraphView, max: Int, graphName: String) {
        graph.title = graphName
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(max.toDouble())
        graph.viewport.isScalable = true
//        graph.viewport.isYAxisBoundsManual = true
//        graph.viewport.isXAxisBoundsManual = true
        graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.HORIZONTAL
        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    // show normal x values
                    super.formatLabel(value, isValueX)
                } else {
                    // show currency for y values
                    if (value > 1000) {
                        super.formatLabel((value / 10000), isValueX) + " K"
                    } else {
                        super.formatLabel(value, isValueX)
                    }
                }
            }
        }
    }
}