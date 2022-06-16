package com.black.xperiments.usbserialcommunication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager

class UsbBroadcastReceiver(var usbStatusInterface:UsbStatusInterface):BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                usbStatusInterface.onStatus("attached")
            }
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                usbStatusInterface.onStatus("detached")
            }
            Constants.ACTION_USB_PERMISSION -> {
                usbStatusInterface.onStatus("granted")
            }
            Constants.ACTION_PORT_OPEN -> {
                usbStatusInterface.onStatus("open")
            }
            Constants.ACTION_PORT_CLOSE -> {
                usbStatusInterface.onStatus("close")
            }
            Constants.ACTION_DATA_AVAILABLE -> {
                usbStatusInterface.onStatus("data")
            }
        }
    }

}