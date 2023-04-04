package com.experiments.imusensor


import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import com.experiments.imusensor.ble.IMUBLEAdapter
import com.experiments.imusensor.databinding.ActivityMainBinding
import com.experiments.imusensor.datamodel.DataViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity(), ServiceConnection()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    /*---- BLE ----- */
    private var serviceBinder = BLEService.LocalBinder
    private val  imubleAdapter =  IMUBLEAdapter(this)

    /*----------- Data --------------*/
    private val dataViewModel: DataViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        /*Permissions*/
        val permissions = listOf(
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.BLUETOOTH_ADVERTISE,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.FOREGROUND_SERVICE,
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        validatePermission(permissions)

        observers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        if(imubleAdapter.checkIfAdapterIsEnable()){
            imubleAdapter.startBleScan(hasRequiredRuntimePermissions())
        }else{
            openBtActivity()
        }
    }
    var btRequestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            Log.d("IMU_BLE", "btRequestActivity RESULT OK")
        } else {
            Log.d("IMU_BLE", "btRequestActivity RESULT NOT OK")
        }
    }

    private fun openBtActivity() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        btRequestActivity.launch(intent)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.unbindService(this)
    }

    private fun observers(){
        dataViewModel.permission.observe(this){
            Log.d("BLUETOOTH","$it")
        }
    }

    /*--- Permission ------------*/
    fun Context.hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionType) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun Context.hasRequiredRuntimePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
                    hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        serviceBinder = service as BLEService.LocalBinder
    }

    override fun onServiceDisconnected(p0: ComponentName?) {

    }

    private fun validatePermission(permissions: List<String>) {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        dataViewModel.permission.value = p0?.areAllPermissionsGranted()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.storage_permission_rationale_title)
                            .setMessage(R.string.storage_permission_rationale_message)
                            .setNegativeButton(
                                android.R.string.cancel
                            ) { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.cancelPermissionRequest()
                            }
                            .setPositiveButton(
                                android.R.string.ok
                            ) { dialogInterface, _ ->
                                dialogInterface.dismiss()
                                p1?.continuePermissionRequest()
                            }
                            .show()
                    }
                }
            ).check()
    }



}