package com.black.xperiments.usbdeviceconnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log

class UsbBroadcastReceiver:BroadcastReceiver() {
    private val TAG = "USB_DEVICES"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Constants.ACTION_USB_PERMISSION == intent?.action) {
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
        }
    }
}