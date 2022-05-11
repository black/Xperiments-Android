package com.black.xperiments.usbdeviceconnection

class Constants {
    companion object{
        // for custom broadcast receiver
        const val ACTION_USB_PERMISSION = "com.black.xperiments.usbdeviceconnection.Constants.Companion.ACTION_USB_PERMISSION"

        const val ACTION_PORT_OPEN = "ACTION_PORT_OPEN"
        const val ACTION_PORT_CLOSE = "ACTION_PORT_CLOSE"
        const val ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE"
        const val EXTRA_DATA = "EXTRA_DATA"
        const val ACTION_USB_DEVICE_DETACHED = "ACTION_USB_DEVICE_DETACHED"
    }
}