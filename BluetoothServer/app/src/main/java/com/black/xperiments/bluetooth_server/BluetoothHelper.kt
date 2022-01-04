package com.black.xperiments.bluetooth_server

import android.bluetooth.BluetoothAdapter
import android.util.Log

class BluetoothHelper {
    private var bluetoothAdapter:BluetoothAdapter?=null
    private var bluetoothDeviceList:List<String>?=null
    init {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(bluetoothAdapter==null){
            Log.d("BLUETOOTHCHECK","No bluetooth device")
        }else if(bluetoothAdapter?.isEnabled == true){
            Log.d("BLUETOOTHCHECK","Enabled")
        }
    }

}