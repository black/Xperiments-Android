package com.black.xperiments.headtracking

import android.Manifest
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
    }

    private fun observers(){

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
}