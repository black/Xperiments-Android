package com.black.xperiments.headtracking

import android.Manifest
import android.app.Dialog
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.black.xperiments.headtracking.databinding.ActivityMainBinding
import com.black.xperiments.headtracking.views.ViewPagerAdapter
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
import com.black.xperiments.headtracking.views.eye.EyeMovementDirection
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener  {
    private lateinit var binding: ActivityMainBinding

    private val TAG = "SENSORVAL"

    private val sensorViewModel: SensorViewModel by viewModels()

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

    private val titles = arrayOf("Eye Tracking", "Head Tracking")
    private var engine: TextToSpeech? = null

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
        observers()
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewsPager.apply {
            adapter = pagerAdapter
            currentItem = 0
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    actionBar?.subtitle = titles[position]
                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {
                    /* set toolbar title here */
                }
            })
        }

        /* This is/was here because it was skipping frames*/
        handler.postDelayed({
            engine = TextToSpeech(this, this)
            observers()
        }, 200)//delayed by 200ms

    }


    private fun observers(){
        sensorViewModel.getEOGConnect().observe(this, {
            eogSensor = it
            invalidateOptionsMenu()
        })

        sensorViewModel.getEOGPower().observe(this, {
            eogPowerProgressView?.progress = it
        })

        sensorViewModel.getEOGAllData().observe(this,{
            if (eogPowerPrev!=it.powerLeft) {
                eogPowerPrev = it.powerLeft
                sensorViewModel.setEOGPower(it.powerLeft)
            }
           // pauseSystemMethod(it)
            val blinkStrength = it.blinkStrength
           // sensorViewModel.setBlink(blinkStrength)
            if (blinkStrength > 20) {
                sensorViewModel.setTrigger("head")
            }
        })

        /* Message Player */
        sensorViewModel.getMessage().observe(this, {
            playMessage(it)
        })

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

    private fun playMessage(msg: String) {
        engine!!.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val results = engine!!.setLanguage(Locale.US)
            if (results == TextToSpeech.LANG_MISSING_DATA
                || results == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Toast.makeText(this, "Not supported", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyEngine()
    }

    private fun destroyEngine() {
        if (engine != null) {
            engine!!.stop()
            engine!!.shutdown()
        }
    }

}