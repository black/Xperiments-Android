package com.black.xperiments.bluetooth_server.bluetooth

import android.bluetooth.BluetoothDevice

interface BluetoothDeviceInterface {
    fun onDevice(device: BluetoothDevice)
}