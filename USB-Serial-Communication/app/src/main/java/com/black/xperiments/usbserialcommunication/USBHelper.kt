package com.black.xperiments.usbserialcommunication

import android.content.Context
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.util.Log

class USBHelper(var context:Context):UsbStatusInterface{

    private var usbReceiver = UsbBroadcastReceiver(this)
    private var usbManager:UsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    private var usbDevice: UsbDevice?=null
    private var deviceConnection: UsbDeviceConnection?=null

    init {
        context.registerReceiver(
            usbReceiver,
            makeIntentFilter()
        )
    }

    fun usbDiscovery(): UsbDevice?{
        val usbList: Map<String, UsbDevice> = usbManager.deviceList!!
        usbList.forEach { (s, device) ->
            Log.d("Spinner","$s $device")
            return if (device.vendorId == Constants.ID_VENDOR && device.productId == Constants.ID_PRODUCT) device else null
        }
        return null
    }

    fun stop(){
        context.unregisterReceiver(usbReceiver)
    }


    private fun makeIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ACTION_USB_PERMISSION)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        intentFilter.addAction(Constants.ACTION_PORT_OPEN)
        intentFilter.addAction(Constants.ACTION_PORT_CLOSE)
        intentFilter.addAction(Constants.ACTION_DATA_AVAILABLE)
        return intentFilter
    }

    override fun onStatus(state: String) {
        when(state){
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                // get the USB device matched with the vendor ID and product ID. In this case JINS sensor ID is used
                usbDevice = usbDiscovery()
            }
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                usbDevice = null
            }
            Constants.ACTION_USB_PERMISSION -> {
                /*synchronized(this) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {

                    } else {
                        usbDevice = null
                        finish()
                    }
                }*/
            }
            Constants.ACTION_PORT_OPEN -> {

            }
            Constants.ACTION_PORT_CLOSE -> {

            }
            Constants.ACTION_DATA_AVAILABLE -> {

            }
        }
    }
}