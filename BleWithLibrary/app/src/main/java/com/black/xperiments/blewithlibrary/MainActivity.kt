package com.black.xperiments.blewithlibrary

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.black.xperiments.blewithlibrary.databinding.ActivityMainBinding
import com.ederdoski.simpleble.utils.BluetoothLEHelper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.ederdoski.simpleble.interfaces.BleCallback
import android.widget.Toast

import android.bluetooth.BluetoothGattCharacteristic

import android.bluetooth.BluetoothGatt

import android.bluetooth.BluetoothProfile

/*
* https://github.com/ederdoski/SimpleBle
*/

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var bleHelper: BluetoothLEHelper?=null
    private var handler = Handler(Looper.getMainLooper())
    private var INSERT_YOUR_SERVICE = ""
    private var INSERT_YOUR_CHARACTERISTIC = ""

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
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        validatePermission(permissions)

        bleHelper = BluetoothLEHelper(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_scan -> {
                scanBleFragment()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun scanBleFragment(){
        val bleFragment = BleFragment.newInstance()
        bleFragment.isCancelable = false
        bleFragment.show(supportFragmentManager, "BleFragment")
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