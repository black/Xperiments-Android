package com.black.xperiments.bluetooth_server

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class BluetoothHelper(var context:Context) {
    private var TAG = "BT_debug"
    private var bluetoothAdapter:BluetoothAdapter?=null
    private var bluetoothDeviceInterface:BluetoothDeviceInterface?=null

    init {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

    }

    fun startBlueTooth(){
        checkBlueTooth()
    }

    fun stopBlueTooth(){
        context.unregisterReceiver(broadCastReceiver)
    }

    private val broadCastReceiver:BroadcastReceiver  = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action
                if(action==BluetoothAdapter.ACTION_STATE_CHANGED){
                    when(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR)){
                        BluetoothAdapter.STATE_OFF ->{
                            Log.d(TAG,"OFF")
                        }
                        BluetoothAdapter.STATE_TURNING_OFF ->{
                            Log.d(TAG,"TURNING OFF")
                        }
                        BluetoothAdapter.STATE_ON ->{
                            Log.d(TAG,"ON")
                        }
                        BluetoothAdapter.STATE_TURNING_ON ->{
                            Log.d(TAG,"TURNING ON")
                        }
                    }
                }
        }
    }

    private fun checkBlueTooth(){
        if(bluetoothAdapter?.isEnabled == false){
            val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivity(enableBTIntent)

            val btIntent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            context.registerReceiver(broadCastReceiver,btIntent)
        }
    }

    fun startDeviceDiscovery(){
        if(bluetoothAdapter?.isDiscovering==true){
           bluetoothAdapter?.cancelDiscovery()
            Log.d(TAG,"STOPPED STARTING DISCOVERY")
        }
        bluetoothAdapter?.startDiscovery()
        Log.d(TAG,"STARTING DISCOVERY")
        val discoverDeviceIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(deviceDiscoveryBroadCastReceiver,discoverDeviceIntent)

        val discoverDeviceIntentFinished = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        context.registerReceiver(deviceDiscoveryBroadCastReceiver,discoverDeviceIntentFinished)
    }

    private val deviceDiscoveryBroadCastReceiver:BroadcastReceiver  = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if(action==BluetoothDevice.ACTION_FOUND){
                val device: BluetoothDevice  = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                bluetoothDeviceInterface?.onDevice(device)
            }else if(action==BluetoothAdapter.ACTION_DISCOVERY_FINISHED){
                Log.d(TAG,"Helper Discovery Ended")
            }
        }
    }

    fun setBluetoothDeviceListener(listener: BluetoothDeviceInterface) {
        bluetoothDeviceInterface = listener
    }

}