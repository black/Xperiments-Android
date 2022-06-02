package com.black.xperiments.usbdeviceconnection

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log

class UsbBroadcastReceiver(context:Context,var device:UsbDevice):BroadcastReceiver() {
    private val TAG = "USB_DEVICES"
    private lateinit var manager:UsbManager

    init {
        val permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(Constants.ACTION_USB_PERMISSION), 0)
        manager.requestPermission(device, permissionIntent)

    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(Constants.ACTION_USB_PERMISSION)){
            val granted = intent?.extras?.getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED)
            if(granted == true){
               // openUSBPort()
                Log.d("Status","Granted")
            }
        }
       /* if (Constants.ACTION_USB_PERMISSION == intent?.action) {
            synchronized(this) {
                val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    device?.apply {
                        //call method to set up device communication
                    }
                } else {
                    Log.d(TAG, "permission denied for device $device")
                }
            }
        }

        if (Constants.ACTION_USB_DEVICE_DETACHED == intent?.action) {
            val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
            device?.apply {
                // call your method that cleans up and closes communication with the device
            }
        }*/
    }


    fun makeIntentFilter(): IntentFilter? {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ACTION_USB_PERMISSION)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        intentFilter.addAction(Constants.ACTION_PORT_OPEN)
        intentFilter.addAction(Constants.ACTION_PORT_CLOSE)
        intentFilter.addAction(Constants.ACTION_DATA_AVAILABLE)
        return intentFilter
    }
}