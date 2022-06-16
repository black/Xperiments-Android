package com.black.xperiments.usbserialcommunication

class Constants {
    companion object{
        // management and control of USB device
        const val ID_VENDOR = 4292
        const val ID_PRODUCT = 60000

        // for custom broadcast receiver
        const val ACTION_USB_PERMISSION = "com.black.xperiments.usbserialcommunication.Constants.Companion.ACTION_USB_PERMISSION"

        const val ACTION_PORT_OPEN = "ACTION_PORT_OPEN"
        const val ACTION_PORT_CLOSE = "ACTION_PORT_CLOSE"
        const val ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE"
        const val EXTRA_DATA = "EXTRA_DATA"
        const val ACTION_USB_DEVICE_DETACHED = "ACTION_USB_DEVICE_DETACHED"
    }
}