package com.black.xperiments.bluetooth_server

import android.bluetooth.BluetoothDevice

interface BluetoothDeviceInterface {
    fun onDevice(device: BluetoothDevice)
}