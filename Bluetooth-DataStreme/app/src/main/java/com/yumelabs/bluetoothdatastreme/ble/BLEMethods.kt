package com.yumelabs.bluetoothdatastreme.ble

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import com.yumelabs.bluetoothdatastreme.MainActivity.Companion.TAG
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleConnectStatus
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleData
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleDeviceInterface
import android.content.Intent
import com.yumelabs.bluetoothdatastreme.ble.DeviceProfile.Companion.ACTION_GATT_SERVICES_DISCOVERED
import android.bluetooth.BluetoothGattCharacteristic

import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothGattDescriptor
import com.yumelabs.bluetoothdatastreme.ble.DeviceProfile.Companion.DESCRIPTOR_UUID


// Top level declaration
private const val GATT_MAX_MTU_SIZE = 517

class BLEMethods(var context: Context,
                 var handler: Handler,
                 bleDevice: BleDeviceInterface,
                 bleConnectStatus: BleConnectStatus,
                 bleData: BleData
){
    private var bluetoothGatt: BluetoothGatt?=null
    private var bluetoothGattCharacteristic: BluetoothGattCharacteristic?=null
    private val bluetoothAdapter:BluetoothAdapter by lazy {
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }
    private var bleDevice: BleDeviceInterface? = null
    private var bleConnectStatus: BleConnectStatus? = null
    private var isScanning = false


    init {
        this.bleDevice = bleDevice
        this.bleConnectStatus = bleConnectStatus
    }

    fun startBleScan() {
        if(isScanning){
            stopBleScan()
        }
         val scanFilter = ScanFilter.Builder().build()
         val scanFilters:MutableList<ScanFilter> = mutableListOf()
         scanFilters.add(scanFilter)
         val scanSettings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
         bluetoothAdapter.bluetoothLeScanner.startScan(scanFilters,scanSettings,bleScanCallback)
    }

    fun stopBleScan(){
        bluetoothAdapter.bluetoothLeScanner.stopScan(bleScanCallback)
    }

    private val bleScanCallback: ScanCallback by lazy {
        object :ScanCallback(){
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                val bluetoothDevice = result?.device
                bleDevice.onBleDevice(bluetoothDevice!!)
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>?) {
                super.onBatchScanResults(results)
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
            }
        }
    }

    fun connectBle(bleDevice: BluetoothDevice){
        bleConnectStatus?.onStatus("CONNECTING...")
        // this fixed the status code 133
        bluetoothGatt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bleDevice.connectGatt(context,false, bluetoothGattCallback,BluetoothDevice.TRANSPORT_LE)
        }else{
            bleDevice.connectGatt(context,false, bluetoothGattCallback)
        }
    }

    private val bluetoothGattCallback:BluetoothGattCallback by lazy{
        object:BluetoothGattCallback(){
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    if(newState==BluetoothProfile.STATE_CONNECTED){
                       bluetoothGatt = gatt
                       handler.post{
                           bluetoothGatt?.discoverServices()
                       }
                        bleConnectStatus.onStatus("CONNECTED")
                    }else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        bleConnectStatus.onStatus("DISCONNECTED")
                        gatt?.close()
                    }
                }else{
                    bleConnectStatus.onStatus("FAILED")
                    gatt?.close()
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        // broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                       // BluetoothGatt.printGattTable()
//                        for (service in services) {
//                            if (service.uuid != UUID_TARGET_SERVICE) continue
//                            val gattCharacteristics = service.characteristics
//
//                            // Loops through available Characteristics.
//                            for (gattCharacteristic in gattCharacteristics) {
//                                if (gattCharacteristic.uuid != UUID_TARGET_CHARACTERISTIC) continue
//                                val charaProp = gattCharacteristic.properties
//                                if (charaProp or BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
//                                    setCharacteristicNotification(gattCharacteristic, true)
//                                } else {
//                                    Log.w(TAG, "Characteristic does not support notify")
//                                }
//                            }
//                        }
                       // Log.d(TAG, "Read characteristic $value")
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                       // Log.d(TAG, "Read not permitted for $uuid!")
                    }
                    else -> {
                     //   Log.d(TAG, "Characteristic read failed for $uuid, error: $status")
                    }
                }
//                if(status==BluetoothGatt.GATT_SUCCESS){
//                    broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
//                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
                bluetoothGattCharacteristic = characteristic
                val value = characteristic?.value
                val uuid = characteristic?.uuid
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        if (value != null) {
                            bleData.onBleData(value.toHexString())
                        }
                        Log.d(TAG, "Read characteristic $value")
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        Log.d(TAG, "Read not permitted for $uuid!")
                    }
                    else -> {
                        Log.d(TAG, "Characteristic read failed for $uuid, error: $status")
                    }
                }
            }

            override fun onCharacteristicWrite(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicWrite(gatt, characteristic, status)
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?
            ) {
                super.onCharacteristicChanged(gatt, characteristic)
            }

            override fun onDescriptorRead(
                gatt: BluetoothGatt?,
                descriptor: BluetoothGattDescriptor?,
                status: Int
            ) {
                super.onDescriptorRead(gatt, descriptor, status)
            }

            override fun onDescriptorWrite(
                gatt: BluetoothGatt?,
                descriptor: BluetoothGattDescriptor?,
                status: Int
            ) {
                super.onDescriptorWrite(gatt, descriptor, status)
            }
        }
    }

    private fun BluetoothGatt.printGattTable() {
        if (services.isEmpty()) {
            Log.i("printGattTable", "No service and characteristic available, call discoverServices() first?")
            return
        }
        services.forEach { service ->
            val characteristicsTable = service.characteristics.joinToString(
                separator = "\n|--",
                prefix = "|--"
            ) { it.uuid.toString() }
            Log.i("printGattTable", "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
            )
        }
    }

    fun setCharacteristicNotification(
        characteristic: BluetoothGattCharacteristic,
        enabled: Boolean
    ) {
        if (bluetoothGatt == null) {
            Log.d(TAG, "BluetoothAdapter not initialized")
            return
        }
        bluetoothGatt?.setCharacteristicNotification(characteristic, enabled)
        val descriptor = characteristic.getDescriptor(DESCRIPTOR_UUID)
        descriptor.value =
            if (enabled) BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
        bluetoothGatt?.writeDescriptor(descriptor)
    }

    fun ByteArray.toHexString(): String =
        joinToString(separator = " ", prefix = "0x") { String.format("%02X", it) }

    fun BluetoothGattCharacteristic.isReadable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)

    fun BluetoothGattCharacteristic.isWritable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)

    fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

    fun BluetoothGattCharacteristic.containsProperty(property: Int): Boolean {
        return properties and property != 0
    }

    private fun broadcastUpdate(action: String) {
        val intent = Intent(action)
       // sendBroadcast(intent)
    }


//    private fun readBatteryLevel() {
//        val batteryLevelChar = gatt
//            .getService(batteryServiceUuid)?.getCharacteristic(batteryLevelChar)
//        if (batteryLevelChar?.isReadable() == true) {
//            gatt.readCharacteristic(batteryLevelChar)
//        }
//    }

}

/*
   https://punchthrough.com/android-ble-guide/
   https://punchthrough.com/android-ble-guide/
*/