package com.black.xperiments.usbmouseperipheral

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.black.xperiments.usbmouseperipheral.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.benlypan.usbhid.UsbHidDevice

import com.benlypan.usbhid.OnUsbHidDeviceListener




class MainActivity : AppCompatActivity() {

    private var TAG = "USB_Debugging"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        validatePermission(permissions)
    }
    fun theUSB(view: android.view.View) {
        test()
        Log.d(TAG,"Conenction Failed")
    }

    private fun test() {
        val device = UsbHidDevice.factory(this, 0x0680, 0x0180) ?: return
        device.open(this, object : OnUsbHidDeviceListener {
            override fun onUsbHidDeviceConnected(device: UsbHidDevice) {
                val sendBuffer = ByteArray(64)
                sendBuffer[0] = 0x01
                sendBuffer[1] = 0x03
                device.write(sendBuffer)
                val result = device.read(64)
                Log.d(TAG,"${result}")
            }

            override fun onUsbHidDeviceConnectFailed(device: UsbHidDevice) {
                Log.d(TAG,"Connection Failed")
            }
        })
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