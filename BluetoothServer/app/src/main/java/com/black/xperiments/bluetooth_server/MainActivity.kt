package com.black.xperiments.bluetooth_server

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.bluetooth_server.bluetooth.BluetoothDeviceAdapter
import com.black.xperiments.bluetooth_server.bluetooth.BluetoothDeviceInterface
import com.black.xperiments.bluetooth_server.bluetooth.BluetoothHelper
import com.black.xperiments.bluetooth_server.bluetooth.OnClickListener
import com.black.xperiments.bluetooth_server.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity(), BluetoothDeviceInterface {

    private lateinit var binding: ActivityMainBinding

    private var TAG = "BT_debug"

    private val handler= Handler(Looper.getMainLooper())
    private var counter = 0
    private var time = 1L
    private var bluetoothHelper: BluetoothHelper?=null
    private var bluetoothDeviceAdapter: BluetoothDeviceAdapter?=null
    private var deviceList = arrayListOf<BluetoothDevice>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        validatePermission(permissions)
        bluetoothHelper = BluetoothHelper(this)
        bluetoothHelper?.setBluetoothDeviceListener(this)

        bluetoothDeviceAdapter = BluetoothDeviceAdapter(deviceList)

        binding.deviceListView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
            adapter = bluetoothDeviceAdapter
        }

        bluetoothDeviceAdapter?.setOnItemClickListener(object : OnClickListener {
            override fun onItemClick(pos: Int) {
                bluetoothHelper?.connectDevice(deviceList[pos])
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothHelper?.stopBlueTooth()
    }

    // speed
    fun starScan(){
        handler.postDelayed(runnable, 0)
    }

    fun stopScan(){
        handler.removeCallbacks(runnable)
    }

    private val runnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                handler.postDelayed(this, time * 1000)
                if(counter<5)counter++
                else counter = 0
            }
        }
    }

    fun turnOnBluetooth(view: android.view.View) {
        bluetoothHelper?.startBlueTooth()
    }

    fun scanBluetoothDevices(view: android.view.View) {
        bluetoothHelper?.startDeviceDiscovery()
    }

    override fun onDevice(device: BluetoothDevice) {
        if(!deviceList.contains(device)) {
            deviceList.add(device)
            bluetoothDeviceAdapter?.notifyItemChanged(deviceList.size-1)
            Log.d(TAG,"${device.address} ${device.name}")
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

}