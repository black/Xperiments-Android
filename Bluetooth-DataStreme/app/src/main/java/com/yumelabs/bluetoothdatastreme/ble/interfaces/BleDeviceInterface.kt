package com.yumelabs.bluetoothdatastreme.ble.interfaces
import android.bluetooth.BluetoothDevice
interface BleDeviceInterface {
    fun onBleDevice(bleDevice: BluetoothDevice)
}