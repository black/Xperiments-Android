package com.black.xperiments.bluetooth_server

import android.bluetooth.*
import android.content.Context
import android.util.Log
import com.black.xperiments.bluetooth_server.bluetooth.BluetoothDeviceInterface
import java.io.IOException
import java.util.*

class BluetoothConnectionServices(var context: Context) {
    private var TAG = "BT_debug"
    private val UUID = java.util.UUID.fromString("e012079a-6bfd-11ec-90d6-0242ac120003")
    private var bluetoothAdapter: BluetoothAdapter?=null
    private var bluetoothDeviceInterface: BluetoothDeviceInterface?=null

    init {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    inner class AcceptThread: Thread() {
        private var bluetoothSeverSocket:BluetoothServerSocket?=null
        init {
            try {
                bluetoothSeverSocket = bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord("BlackBlue",UUID)
            }catch (e:IOException){
                Log.d(TAG,"$e")
            }
        }

        override fun run() {
            var socket:BluetoothSocket?=null
            try {
                socket = bluetoothSeverSocket?.accept()
            }catch (e:IOException){
                Log.d(TAG,"$e")
            }

            if (socket!=null){
            //    connected(socket,device)
            }
        }

        fun cancelSocket(){
            try {
                bluetoothSeverSocket?.close()
            }catch (e:IOException){
                Log.d(TAG,"$e")
            }
        }
    }

    inner class ConnectThread(var device: BluetoothDevice,var uuid: UUID): Thread() {

        override fun run() {
            var bluetoothSocket:BluetoothSocket?=null
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
            }catch (e:IOException){
                Log.d(TAG,"$e")
            }

        }
    }
}