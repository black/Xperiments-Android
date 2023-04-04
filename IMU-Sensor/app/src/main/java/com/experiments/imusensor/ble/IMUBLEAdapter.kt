package com.experiments.imusensor.ble

import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.experiments.imusensor.ble.DeviceProfile.Companion.CHARACTERISTIC_STATE_UUID_ACCEL_X
import com.experiments.imusensor.ble.DeviceProfile.Companion.SERVICE_UUID

class IMUBLEAdapter(private val activity: Activity) {

    companion object{
        private val TAG = "IMU_BLE"
        val BLUETOOTH_REQUEST_CODE = 1
    }



    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        (activity.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    private var bluetoothGatt: BluetoothGatt?=null
    fun checkIfAdapterIsEnable():Boolean{
       return bluetoothAdapter.isEnabled
    }

    /*--- scan ble ------------ */
    fun startBleScan(permission:Boolean){
        // FILTER DEVICE BASED ON THIS NAME
        val scanFilter = ScanFilter.Builder().setDeviceName("WearSenseIMU").build()
        val scanFilters:MutableList<ScanFilter> = mutableListOf()
        scanFilters.add(scanFilter)

        /*---------------*/
        if(permission){
            val scanSettings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
            bleScanner.startScan(
                scanFilters,
                scanSettings,
                bleScanCallback)
        }
    }

    private val bleScanCallback:ScanCallback by lazy {
        object : ScanCallback(){
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)

                val bluetoothDevice = result?.device
                with(bluetoothDevice) {
                    if(bluetoothDevice!=null){
                        Log.v(TAG,"Device Name ${this?.name} Device Address ${this?.uuids}")
                    }
                }
            }
        }
    }


    /*--- connect ble ------------ */
    fun connectDevice(device: BluetoothDevice){
        bluetoothGatt =  device.connectGatt(activity, false, bleGattCallback)
    }

    private val bleGattCallback : BluetoothGattCallback by lazy {
        object : BluetoothGattCallback(){
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                when(newState){
                    BluetoothProfile.STATE_CONNECTING -> Log.d(TAG,"Connecting")
                    BluetoothProfile.STATE_CONNECTED -> Log.d(TAG,"Connected")
                    BluetoothProfile.STATE_DISCONNECTING -> Log.d(TAG,"disconnecting")
                    BluetoothProfile.STATE_DISCONNECTED -> Log.d(TAG,"disconnected")
                    else -> Log.d(TAG,"Prepare")
                }
            }

            override fun onServiceChanged(gatt: BluetoothGatt) {
                super.onServiceChanged(gatt)
                val service = gatt.getService(SERVICE_UUID)

                val characteristic = service.getCharacteristic(CHARACTERISTIC_STATE_UUID_ACCEL_X)
                characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                gatt.setCharacteristicNotification(characteristic,true)
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, value, status)
                with(characteristic) {
                    when (status) {
                        BluetoothGatt.GATT_SUCCESS -> {
                            Log.i("BluetoothGattCallback", "Read characteristic $uuid:\n${value.toHexString()}")
                        }
                        BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                            Log.e("BluetoothGattCallback", "Read not permitted for $uuid!")
                        }
                        else -> {
                            Log.e("BluetoothGattCallback", "Characteristic read failed for $uuid, error: $status")
                        }
                    }
                }
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray
            ) {
                super.onCharacteristicChanged(gatt, characteristic, value)

            }
        }
    }

    fun ByteArray.toHexString(): String =
        joinToString(separator = " ", prefix = "0x") { String.format("%02X", it) }

    /*--- read ble data ------------ */





}