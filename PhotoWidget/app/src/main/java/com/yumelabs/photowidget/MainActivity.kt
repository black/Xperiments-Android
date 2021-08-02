package com.yumelabs.photowidget

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.yumelabs.photowidget.commons.ImageAdapter
import com.yumelabs.photowidget.commons.ImageReader
import com.yumelabs.photowidget.commons.OnItemClickListener
import com.yumelabs.photowidget.commons.Picture
import com.yumelabs.photowidget.databinding.ActivityMainBinding
import com.yumelabs.photowidget.extensions.SpacesItemDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var imageReader: ImageReader?=null
    private var imageList = ArrayList<Picture>()
    private var imageAdapter: ImageAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        validatePermission(permissions)

        imageReader = ImageReader(this)
        imageList = imageReader!!.getAllImage()

        imageAdapter = ImageAdapter(this,R.layout.layout_image, imageList)
        binding.imageList.post {
            val count = 3
            binding.imageList.adapter  = imageAdapter
            val mLayoutManager = GridLayoutManager(this, count)
            binding.imageList.layoutManager = mLayoutManager
            binding.imageList.addItemDecoration(
                SpacesItemDecoration(
                    count,
                    resources.getDimensionPixelSize(R.dimen.spacing_none),
                    false
                )
            )
            imageAdapter!!.setOnItemClickListener(object: OnItemClickListener {
                override fun onItemClick(pos: Int) {

                }
            })
        }
    }

    private fun validatePermission(permissions: List<String>) {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
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