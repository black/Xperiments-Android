package com.black.xperiments.bluetooth_server

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.black.xperiments.bluetooth_server.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity(),BluetoothDeviceInterface {

    private lateinit var binding: ActivityMainBinding

    private var TAG = "BT_debug"
    private val UUID = "e012079a-6bfd-11ec-90d6-0242ac120003"

    private val handler= Handler(Looper.getMainLooper())
    private var counter = 0
    private var time = 1L
    private var bluetoothHelper:BluetoothHelper?=null
    private var bluetoothDeviceAdapter:BluetoothDeviceAdapter?=null
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

        bluetoothDeviceAdapter = BluetoothDeviceAdapter(this, deviceList)
        binding.deviceListView.apply {
            adapter = bluetoothDeviceAdapter
        }
        bluetoothDeviceAdapter?.setOnItemClickListener(object :OnClickListener{
            override fun onItemClick(pos: Int) {

            }
        })
        bluetoothHelper = BluetoothHelper(this)
        bluetoothHelper?.setBluetoothDeviceListener(this)
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
         deviceList.add(device)
         Log.d(TAG,"MainActivity ${device.address} ${device.name}")
         bluetoothDeviceAdapter?.notifyDataSetChanged()
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