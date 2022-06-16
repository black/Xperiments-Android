package com.black.xperiments.usbserialcommunication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.black.xperiments.usbserialcommunication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

    /*
    TUTORIAL
    https://developer.android.com/guide/topics/connectivity/usb/host
    */

    private lateinit var binding: ActivityMainBinding

    private var usbHelper:USBHelper?=null

    /*---- Communicate with the device -------*/
    private lateinit var bytes: ByteArray
    private val TIMEOUT = 0
    private val forceClaim = true
    private var baudRate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectBaudRate()
        usbHelper = USBHelper(this )
    }

    private fun selectBaudRate(){
        binding.baudRateList.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, p3: Long) {
                val selectedItem = parent?.getItemAtPosition(pos).toString()
               // baudRate = selectedItem.toInt()
                Log.d("Spinner","$selectedItem")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }


    /*private fun usbOpen(usbDevice:UsbDevice){
        *//*-------------------------------------------------*//*
        val permissionIntent = PendingIntent.getBroadcast(this, 0, Intent(Constants.ACTION_USB_PERMISSION), 0)
        val filter = usbReceiver?.makeIntentFilter()
        registerReceiver(usbReceiver, filter)
        usbManager.requestPermission(device, permissionIntent)

        device?.getInterface(0).also { intf ->
            intf?.getEndpoint(0)?.also { endpoint ->
                usbManager.openDevice(device)?.apply {
                    claimInterface(intf, forceClaim)
                    bulkTransfer(endpoint, bytes, bytes.size, TIMEOUT) //do in another thread
                }
            }
        }
    }*/

}