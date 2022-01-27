package com.black.xperiments.blewithlibrary

import android.bluetooth.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.blewithlibrary.bleview.BleDeviceAdapter
import com.black.xperiments.blewithlibrary.databinding.FragmentBleBinding
import com.black.xperiments.eegchannelfour.bleview.OnClickListener
import com.ederdoski.simpleble.interfaces.BleCallback
import com.ederdoski.simpleble.models.BluetoothLE
import com.ederdoski.simpleble.utils.BluetoothLEHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class BleFragment : BottomSheetDialogFragment(),
    ViewBindingHolder<FragmentBleBinding> by ViewBindingHolderImpl(){
    private var TAG = "MyBle"
    private var bleHelper: BluetoothLEHelper?=null
    private var bleDeviceAdapter:BleDeviceAdapter?=null
    private var deviceList = mutableListOf<BluetoothLE>()
    private var handler = Handler(Looper.getMainLooper())

    // EOG Sensor UUID and Characteristics
//    private var ADVERTISE_SERVICE_EOG_UUID = "f5dc3761-ce15-4449-8cfa-7af6ad175056"
//    private var READ_EOG_CHARACTERISTIC_UUID = "f5dc3764-ce15-4449-8cfa-7af6ad175056"
//    private var WRITABLE_EOG_CHARACTERISTIC_UUID = "f5dc3762-ce15-4449-8cfa-7af6ad175056"

//    EEG Sensor UUID and Characteristics
    private var ADVERTISE_SERVICE_EEG_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
    private var NOTIFICATION_EEG_CHARACTERISTIC_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"
    private var WRITABLE_EEG_CHARACTERISTIC_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
    private var DESCRIPTORS_EEG_CHARACTERISTIC_UUID = "00002902-0000-1000-8000-00805f9b34fb"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View = initBinding(FragmentBleBinding.inflate(layoutInflater), this) {
        bleHelper = BluetoothLEHelper(requireActivity())
        initBle()
        binding?.dismissFragment?.setOnClickListener {
            dismiss()
        }

        binding?.startData?.setOnClickListener {
            if (bleHelper?.isConnected == true) {
                bleHelper?.read(ADVERTISE_SERVICE_EEG_UUID, NOTIFICATION_EEG_CHARACTERISTIC_UUID)
                bleHelper?.write(ADVERTISE_SERVICE_EEG_UUID,NOTIFICATION_EEG_CHARACTERISTIC_UUID,BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
//                val aBytes = ByteArray(20)
//                aBytes[0] = (0x14 and 0xFF).toByte()
//                aBytes[1] = (0x24 and 0xFF).toByte()
//                aBytes[2] = (0x96 and 0xFF).toByte()
//                aBytes[3] = (0x7F and 0xFF).toByte()
//                aBytes[4] = (0x3F and 0xFF).toByte()
//                aBytes[5] = (0x6B and 0xFF).toByte()
//                aBytes[6] = (0x7F and 0xFF).toByte()
//                aBytes[7] = (0x6C and 0xFF).toByte()
//                aBytes[8] = (0x9B and 0xFF).toByte()
//                aBytes[9] = (0xFF and 0xFF).toByte()
//                aBytes[10] = (0x88 and 0xFF).toByte()
//                aBytes[11] = (0x6F and 0xFF).toByte()
//                aBytes[12] = (0xC5 and 0xFF).toByte()
//                aBytes[13] = (0xB4 and 0xFF).toByte()
//                aBytes[14] = (0x60 and 0xFF).toByte()
//                aBytes[15] = (0x44 and 0xFF).toByte()
//                aBytes[16] = (0xE0 and 0xFF).toByte()
//                aBytes[17] = (0x87 and 0xFF).toByte()
//                aBytes[18] = (0x06 and 0xFF).toByte()
//                aBytes[19] = (0x00 and 0xFF).toByte()
//                bleHelper?.write(ADVERTISE_SERVICE_EOG_UUID, WRITABLE_EOG_CHARACTERISTIC_UUID, aBytes)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        scanBle()
    }

    private fun initBle(){
        bleDeviceAdapter = BleDeviceAdapter(deviceList)
        binding?.deviceListView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = bleDeviceAdapter
        }

        bleDeviceAdapter?.setOnItemClickListener(object : OnClickListener {
            override fun onItemClick(pos: Int) {
                Log.d(TAG,"$pos ${deviceList[pos]} ")
                connectBle(deviceList[pos].device)
            }
        })
    }


    private fun scanBle(){
        if(bleHelper?.isScanning==false) {
            if (bleHelper?.isReadyForScan == true) {
                bleHelper?.scanLeDevice(true)
                binding?.scanProgress?.visibility = VISIBLE
                handler.postDelayed({
                    deviceList.clear()
                    deviceList.addAll(bleHelper?.listDevices!!)
                    bleDeviceAdapter?.notifyItemChanged(deviceList.size - 1)
                    binding?.scanProgress?.visibility = GONE
                    Log.d(TAG, "${bleHelper?.listDevices}");
                }, bleHelper?.scanPeriod!!)
            }
        }
    }

    fun connectBle(bleDevice: BluetoothDevice){
        bleHelper?.connect(bleDevice, bleCallbacks)
    }

    fun disconnectBle(){
        bleHelper?.disconnect()
    }

    private var bleCallbacks: BleCallback =  object : BleCallback() {
        override fun onBleConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            super.onBleConnectionStateChange(gatt, status, newState)
            when(newState){
                BluetoothProfile.STATE_CONNECTED->{
                    Log.d(TAG, "Connected to GATT server.")
                }
                BluetoothProfile.STATE_DISCONNECTED->{
                    Log.d(TAG, "Disconnected to GATT server.")
                }
            }
        }

        override fun onBleServiceDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onBleServiceDiscovered(gatt, status)
            when(status){
                BluetoothGatt.GATT_SUCCESS->{
                 Log.d(TAG, "onServicesDiscovered received: $status")
                }
                BluetoothGatt.GATT_FAILURE->{
                   // Log.d(TAG, "onServicesDiscovered failed: $status")
                }
            }
        }

        override fun onBleCharacteristicChange(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onBleCharacteristicChange(gatt, characteristic)
            Log.d(TAG, "onCharacteristicChanged Value: ${Arrays.toString(characteristic.value)}")
        }

        override fun onBleRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            super.onBleRead(gatt, characteristic, status)
            when(status){
                BluetoothGatt.GATT_SUCCESS->{
                  Log.d(TAG, "Characteristic: ${Arrays.toString(characteristic.value)}")
                }
                BluetoothGatt.GATT_FAILURE->{

                }
            }
        }

        override fun onBleWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            super.onBleWrite(gatt, characteristic, status)
//            runOnUiThread {
//                Toast.makeText(
//                    this@MainActivity,
//                    "onCharacteristicWrite Status : $status",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BleFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}