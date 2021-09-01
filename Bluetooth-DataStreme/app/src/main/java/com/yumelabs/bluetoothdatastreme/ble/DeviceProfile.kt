package com.yumelabs.bluetoothdatastreme.ble

import java.util.*

class DeviceProfile {
    companion object{
        var SERVICE_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
        var CHARACTERISTIC_STATE_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
        var POWER_STATE_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    }
    /*
    GATT Service
    A collection of characteristics (data fields) that describes a feature of a device, e.g.
    the Device Information service can contain a characteristic representing the serial number
    of the device, and another characteristic representing the battery level of the device.
   */

    /*
    GATT Characteristic
    An entity containing meaningful data that can typically be read from or written to, e.g.
    the Serial Number String characteristic.
   */

    /*
    GATT Descriptor
    A defined attribute that describes the characteristic that it’s attached to, e.g. the Client
    Characteristic Configuration descriptor shows if the central is currently subscribed to a
    characteristic’s value change.
   */

    /*
    Notifications
    A means for a BLE peripheral to notify the central when a characteristic’s value changes.
    The central doesn’t need to acknowledge that it’s received the packet.
   */

    /*
    Indictations
    Same as an indication, except each data packet is acknowledged by the central. This guarantees
    their delivery at the cost of throughput.
    */

    /*
    UUID
    Universally unique identifier, 128-bit number used to identify services, characteristics and descriptors.
     */
}