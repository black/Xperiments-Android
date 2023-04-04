package com.black.instadownloader

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.black.instadownloader.common.AsyncTask
import com.black.instadownloader.common.DownloadViewModel
import com.black.instadownloader.databinding.ActivityMainBinding
import com.black.instadownloader.manager.InstaAdapter
import com.black.instadownloader.manager.InstaFile
import com.black.instadownloader.manager.OnClickListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val asyncExample: AsyncTask by lazy { ViewModelProvider(this)[AsyncTask::class.java] }

    private var downloader : Downloader? = null
    private val downloadViewModel: DownloadViewModel by viewModels()

    /*---Read Files-------*/
    private var downloadList = ArrayList<InstaFile>()
    private var instaAdapter: InstaAdapter? = null

    /*-------------*/
    private val fakeURL = "https://cdn.pixabay.com/photo/2018/04/26/16/31/marine-3352341_960_720.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )

        downloader = Downloader(this,downloadViewModel)
        validatePermission(permissions)
        initList()
    }

    fun startDownload(view: View) {
        // get text from edit box
        val editView  = (binding.downloadURI.text)
        val downloadURI = editView.toString() + "embed" + "/captioned"
        editView.clear()

        // add text to download
        downloadList.add(InstaFile(downloadURI,0,true))
        instaAdapter?.notifyItemChanged(downloadList.size-1)

        // start download
        doSomeSyncTask(downloadURI,downloadList.size-1)
    }

    // download using http using async
    private fun doSomeSyncTask(uri:String,pos:Int){
        asyncExample.execute(onPreExecute = {
            downloadList[pos].dowloadStatus = true
            Toast.makeText(this,"started",Toast.LENGTH_LONG).show()
        }, doInBackground = {
            // download file here
            downloader?.getContent(fakeURL)
        }, onPostExecute = {
            Toast.makeText(this,"Finished",Toast.LENGTH_LONG).show()
            downloadList[pos].dowloadStatus = false
            // once download save here
            it.apply {
//                downloader?.saveToStorage(it,"InstaImage")
            }
        })
    }

    // initialize download list
    private fun initList(){
        instaAdapter = InstaAdapter(downloadList)
        val mLayoutManager = LinearLayoutManager(this)
        binding.downloadListView.apply {
            adapter = instaAdapter
            layoutManager = mLayoutManager
        }
        instaAdapter?.setOnClickListener(object : OnClickListener {
            override fun onClick(pos: Int) {
                Log.d("Download-Info","$pos")
            }
        })
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