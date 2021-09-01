package com.yumelabs.bluetoothdatastreme.ble

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Handler
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleConnectStatus
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleDeviceInterface

class BLEMethods(var context: Context,
                 var handler: Handler,
                 bleDevice: BleDeviceInterface,
                 bleConnectStatus: BleConnectStatus
){
    private var bluetoothGatt: BluetoothGatt?=null
    private var bluetoothGattCharacteristic: BluetoothGattCharacteristic?=null
    private val bluetoothAdapter:BluetoothAdapter by lazy {
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }
    private var bleDevice: BleDeviceInterface? = null
    private var bleConnectStatus: BleConnectStatus? = null

    init {
        this.bleDevice = bleDevice
        this.bleConnectStatus = bleConnectStatus
    }

    fun startBleScan() {
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
                if(bluetoothDevice!= null)  {
                    bleDevice.getBleDevice(bluetoothDevice)
                }
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
        bluetoothGatt = bleDevice.connectGatt(context,false, bluetoothGattCallback)
    }

    private val bluetoothGattCallback:BluetoothGattCallback by lazy{
        object:BluetoothGattCallback(){
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                if(newState==BluetoothProfile.STATE_CONNECTED){
                    bluetoothGatt?.discoverServices()
                    bleConnectStatus.status("CONNECTED")
                }else{
                    bleConnectStatus.status("FAILED")
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                val service = gatt?.getService(DeviceProfile.SERVICE_UUID)
                bluetoothGattCharacteristic = service?.getCharacteristic(DeviceProfile.CHARACTERISTIC_STATE_UUID)
                gatt?.setCharacteristicNotification(bluetoothGattCharacteristic,true)
                // readData()
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
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

    fun readDataStream(){

    }

    fun disConnectBle() {

    }

}