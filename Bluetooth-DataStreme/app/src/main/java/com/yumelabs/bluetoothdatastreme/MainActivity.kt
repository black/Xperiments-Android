package com.yumelabs.bluetoothdatastreme

import android.Manifest
import android.bluetooth.*
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.yumelabs.bluetoothdatastreme.databinding.ActivityMainBinding
import com.yumelabs.bluetoothdatastreme.ui.BLEDeviceAdapter
import com.yumelabs.bluetoothdatastreme.ui.OnItemClickListener
import java.util.*
import com.yumelabs.bluetoothdatastreme.ble.BLEMethods
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleConnectStatus
import com.yumelabs.bluetoothdatastreme.ble.interfaces.BleDeviceInterface


class MainActivity : AppCompatActivity(),BleConnectStatus,BleDeviceInterface {

    companion object{
        val TAG = "BT_SCANNER"
        val BLUETOOTH_REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityMainBinding

    private val bluetoothAdapter:BluetoothAdapter by lazy {
        (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    private var handler = Handler(Looper.getMainLooper())
    private var bleDeviceAdapter: BLEDeviceAdapter? = null
    private var deviceList = arrayListOf<BluetoothDevice>()
    private var bleMethods: BLEMethods?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH
        )
        validatePermission(permissions)

        bleMethods = BLEMethods(this,handler,this,this)
        bleDeviceAdapter = BLEDeviceAdapter(deviceList)
        binding.deviceListView.adapter = bleDeviceAdapter
        binding.deviceListView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        bleDeviceAdapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(pos: Int) {
                bleMethods?.connectBle(deviceList[pos])
            }

            override fun onLongItemClick(pos: Int) {

            }
        })
    }

    private var i = 0
    override fun onResume() {
        super.onResume()
        if(bluetoothAdapter.isEnabled){
            bleMethods?.startBleScan()
            handler.postDelayed({
                bleMethods?.stopBleScan()
                binding.scanProgress.visibility = GONE
            }, 10000)

        }else{
            Log.d(TAG, "BT is disabled")
            val btIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(btIntent, BLUETOOTH_REQUEST_CODE)
        }
    }


    override fun status(connectStatus: String) {
        binding.bleStatus.text = connectStatus
    }

    override fun getBleDevice(bleDevice: BluetoothDevice) {
        if(!deviceList.contains(bleDevice)){
            deviceList.add(bleDevice)
            bleDeviceAdapter?.notifyDataSetChanged()
            binding.scanProgress.visibility = VISIBLE
        }
        Log.d(TAG," Device name:  ${bleDevice.name} - Device id: ${bleDevice.address}")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            BLUETOOTH_REQUEST_CODE->{
                Toast.makeText(this,"Bluetooth is On",Toast.LENGTH_SHORT).show()
            }
            else->{
                Toast.makeText(this,"Bluetooth is off",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validatePermission(permissions: List<String>) {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.storage_permission_rationale_title)
                            .setMessage(R.string.storage_permission_rationale_message)
                            .setNegativeButton(
                                android.R.string.cancel,
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    p1?.cancelPermissionRequest()
                                })
                            .setPositiveButton(
                                android.R.string.ok,
                                DialogInterface.OnClickListener { dialogInterface, _ ->
                                    dialogInterface.dismiss()
                                    p1?.continuePermissionRequest()
                                })
                            .show()
                    }
                }
            ).check()
    }

}
