package com.black.xperiments.bluetooth_server

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.black.xperiments.bluetooth_server.blemodule.BLEDeviceManager
import com.black.xperiments.bluetooth_server.blemodule.BleDeviceData
import com.black.xperiments.bluetooth_server.blemodule.OnDeviceScanListener
import com.black.xperiments.bluetooth_server.bluetooth.BluetoothDeviceAdapter
import com.black.xperiments.bluetooth_server.bluetooth.BluetoothHelper
import com.black.xperiments.bluetooth_server.databinding.ActivityMainBinding
import com.black.xperiments.bluetooth_server.blemodule.BleConnectionManager
import com.black.xperiments.bluetooth_server.blemodule.BleConstant
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity(),
    OnDeviceScanListener,
    View.OnClickListener  {

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"

    private val handler= Handler(Looper.getMainLooper())
    private var counter = 0
    private var time = 1L
    private var bluetoothHelper: BluetoothHelper?=null
    private var bluetoothDeviceAdapter: BluetoothDeviceAdapter?=null
    private var deviceList = arrayListOf<BluetoothDevice>()

    private lateinit var mBtnReadConnectionChar: Button
    private lateinit var mBtnReadBatteryLevel: Button
    private lateinit var mBtnReadEmergency: Button
    private lateinit var mBtnWriteEmergency: Button
    private lateinit var mBtnWriteConnection: Button
    private lateinit var mBtnWriteBatteryLevel: Button
    private lateinit var mTvResult: TextView

    private val REQUEST_LOCATION_PERMISSION = 2018
    private val REQUEST_ENABLE_BT = 1000

    private var mDeviceAddress: String = ""

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
//        bluetoothHelper = BluetoothHelper(this)
//        bluetoothHelper?.setBluetoothDeviceListener(this)
//
//        bluetoothDeviceAdapter = BluetoothDeviceAdapter(deviceList)
//
//        binding.deviceListView.apply {
//            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
//            adapter = bluetoothDeviceAdapter
//        }
//
//        bluetoothDeviceAdapter?.setOnItemClickListener(object : OnClickListener {
//            override fun onItemClick(pos: Int) {
//                bluetoothHelper?.connectDevice(deviceList[pos])
//            }
//        })

        initBLEModule()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        bluetoothHelper?.stopBlueTooth()
//    }

    /**
     *After receive the Location Permission, the Application need to initialize the
     * BLE Module and BLE Service
     */
    private fun initBLEModule() {
        // BLE initialization
        if (!BLEDeviceManager.init(this)) {
            Toast.makeText(this, "BLE NOT SUPPORTED", Toast.LENGTH_SHORT).show()
            return
        }
        registerServiceReceiver()
        BLEDeviceManager.setListener(this)

        if (!BLEDeviceManager.isEnabled()) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        BleConnectionManager.initBLEService(this@MainActivity)
    }


    /**
     * Register GATT update receiver
     */
    private fun registerServiceReceiver() {
        this.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())
    }



    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            when {
                BleConstant.ACTION_GATT_CONNECTED == action -> {
                    Log.i(TAG, "ACTION_GATT_CONNECTED ")
                    BleConnectionManager.findBLEGattService(this@MainActivity)
                }
                BleConstant.ACTION_GATT_DISCONNECTED == action -> {
                    Log.i(TAG, "ACTION_GATT_DISCONNECTED ")
                }
                BleConstant.ACTION_GATT_SERVICES_DISCOVERED == action -> {
                    Log.i(TAG, "ACTION_GATT_SERVICES_DISCOVERED ")
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    BleConnectionManager.findBLEGattService(this@MainActivity)
                }
                BleConstant.ACTION_DATA_AVAILABLE == action -> {
                    val data = intent.getStringExtra(BleConstant.EXTRA_DATA)
                    val uuId = intent.getStringExtra(BleConstant.EXTRA_UUID)
                    Log.i(TAG, "ACTION_DATA_AVAILABLE $data")

                }
                BleConstant.ACTION_DATA_WRITTEN == action -> {
                    val data = intent.getStringExtra(BleConstant.EXTRA_DATA)
                    Log.i(TAG, "ACTION_DATA_WRITTEN ")
                }
            }
        }
    }

    /**
     * Intent filter for Handling BLEService broadcast.
     */
    private fun makeGattUpdateIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BleConstant.ACTION_GATT_CONNECTED)
        intentFilter.addAction(BleConstant.ACTION_GATT_DISCONNECTED)
        intentFilter.addAction(BleConstant.ACTION_GATT_SERVICES_DISCOVERED)
        intentFilter.addAction(BleConstant.ACTION_DATA_AVAILABLE)
        intentFilter.addAction(BleConstant.ACTION_DATA_WRITTEN)

        return intentFilter
    }

    /**
     * Unregister GATT update receiver
     */
    private fun unRegisterServiceReceiver() {
        try {
            this.unregisterReceiver(mGattUpdateReceiver)
        } catch (e: Exception) {
            //May get an exception while user denies the permission and user exists the app
            Log.e(TAG, e.message.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        BleConnectionManager.disconnect()
        unRegisterServiceReceiver()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_scan ->
                scanDevice(false)
            R.id.btn_read_connection ->
                readMissedConnection()
            R.id.btn_read_battery ->
                readBatteryLevel()
            R.id.btn_read_emergency ->
                readEmergencyGatt()
            R.id.btn_write_emergency ->
                writeEmergency()
            R.id.btn_write_battery ->
                writeBattery()
            R.id.btn_write_connection ->
                writeMissedConnection()
        }
    }

    private fun writeEmergency() {
        BleConnectionManager.writeEmergencyGatt("0xfe");
    }

    private fun writeBattery() {
        BleConnectionManager.writeBatteryLevel("100")
    }

    private fun writeMissedConnection() {
        BleConnectionManager.writeMissedConnection("0x00")
    }

    private fun readMissedConnection() {
        BleConnectionManager.readMissedConnection(getString(R.string.char_uuid_missed_calls))
    }

    private fun readBatteryLevel() {
        BleConnectionManager.readBatteryLevel(getString(R.string.char_uuid_emergency))
    }

    private fun readEmergencyGatt() {
        BleConnectionManager.readEmergencyGatt(getString(R.string.char_uuid_emergency))
    }

    /**
     * Scan the BLE device if the device address is null
     * else the app will try to connect with device with existing device address.
     */
    private fun scanDevice(isContinuesScan: Boolean) {
        if (!mDeviceAddress.isNullOrEmpty()) {
            connectDevice()
        } else {
            BLEDeviceManager.scanBLEDevice(isContinuesScan)
        }
    }

    /**
     * Connect the application with BLE device with selected device address.
     */
    private fun connectDevice() {
        Handler().postDelayed({
            BleConnectionManager.initBLEService(this@MainActivity)
            if (BleConnectionManager.connect(mDeviceAddress)) {
                Toast.makeText(this@MainActivity, "DEVICE CONNECTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "DEVICE CONNECTION FAILED", Toast.LENGTH_SHORT).show()
            }
        }, 100)
    }

    override fun onScanCompleted(deviceDataList: BleDeviceData) {
        mDeviceAddress = deviceDataList.mDeviceAddress
        BleConnectionManager.connect(deviceDataList.mDeviceAddress)
    }

    // speed
//    fun starScan(){
//        handler.postDelayed(runnable, 0)
//    }
//
//    fun stopScan(){
//        handler.removeCallbacks(runnable)
//    }

//    private val runnable: Runnable by lazy {
//        object : Runnable {
//            override fun run() {
//                handler.postDelayed(this, time * 1000)
//                if(counter<5)counter++
//                else counter = 0
//            }
//        }
//    }

//    fun turnOnBluetooth(view: android.view.View) {
//        bluetoothHelper?.startBlueTooth()
//    }
//
//    fun scanBluetoothDevices(view: android.view.View) {
//        bluetoothHelper?.startDeviceDiscovery()
//    }
//
//    override fun onDevice(device: BluetoothDevice) {
//        if(!deviceList.contains(device)) {
//            deviceList.add(device)
//            bluetoothDeviceAdapter?.notifyItemChanged(deviceList.size-1)
//            Log.d(TAG,"${device.address} ${device.name}")
//        }
//    }

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