package com.black.xperiments.usbdeviceconnection

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED
import android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.black.xperiments.usbdeviceconnection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /*
    TUTORIAL
    https://developer.android.com/guide/topics/connectivity/usb/host
    */

    private lateinit var binding: ActivityMainBinding
    private lateinit var manager:UsbManager
    private var usbReceiver:UsbBroadcastReceiver?=null
    private var device: UsbDevice?=null
    private var deviceConnection: UsbDeviceConnection?=null


    /*---- Communicate with the device -------*/
    private lateinit var bytes: ByteArray
    private val TIMEOUT = 0
    private val forceClaim = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*-------------------------------------------------*/
        usbReceiver = UsbBroadcastReceiver()
        val permissionIntent = PendingIntent.getBroadcast(this, 0, Intent(Constants.ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(Constants.ACTION_USB_PERMISSION)
        registerReceiver(usbReceiver, filter)
        manager.requestPermission(device, permissionIntent)

        device?.getInterface(0).also { intf ->
            intf?.getEndpoint(0)?.also { endpoint ->
                manager.openDevice(device)?.apply {
                    claimInterface(intf, forceClaim)
                    bulkTransfer(endpoint, bytes, bytes.size, TIMEOUT) //do in another thread
                }
            }
        }
    }

    /*private fun makeIntentFilter(): IntentFilter? {
        // set intent filter for custom　broadcast receiver
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ACTION_USB_PERMISSION)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        intentFilter.addAction(Constants.ACTION_PORT_OPEN)
        intentFilter.addAction(Constants.ACTION_PORT_CLOSE)
        intentFilter.addAction(Constants.ACTION_DATA_AVAILABLE)
        return intentFilter
    }*/
}