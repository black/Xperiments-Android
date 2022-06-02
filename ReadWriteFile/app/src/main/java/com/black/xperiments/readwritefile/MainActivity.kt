package com.black.xperiments.readwritefile

import android.Manifest
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import ca.nuro.nuos.fear.common.AsyncTask
import com.black.xperiments.readwritefile.common.MyFile
import com.black.xperiments.readwritefile.common.OnClickListener
import com.black.xperiments.readwritefile.common.SpacesItemDecoration
import com.black.xperiments.readwritefile.databinding.ActivityMainBinding
import com.black.xperiments.readwritefile.read.MyFileReader
import com.black.xperiments.readwritefile.write.MyFileWriter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val asyncTask: AsyncTask by lazy { ViewModelProvider(this)[AsyncTask::class.java] }
    private var handler = Handler(Looper.getMainLooper())

    /*---Write Files-------*/
    private val fileWriter = MyFileWriter()
    private var isWriting = false
    private val writeFileList = arrayListOf<File>()

    /*---Read Files-------*/
    private var fileReader: MyFileReader?=null
    private var readFileList = ArrayList<MyFile>()
    private var fileAdapter: FileAdapter? = null

    private val shareFilesList: ArrayList<Uri> = arrayListOf()
    private var fileName:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        validatePermission(permissions)


        initReader()
        initWriter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_write ->{
                isWriting = !isWriting
                invalidateOptionsMenu()
                Toast.makeText(applicationContext,if(isWriting) "Recording Started" else "Recording stopped", Toast.LENGTH_LONG).show()
            }
            R.id.action_share ->{
                shareFile()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_write)?.icon = ContextCompat.getDrawable(
            this,
            if (isWriting) R.drawable.ic_action_recorder_started else R.drawable.ic_action_recorder_stopped
        )
        return super.onPrepareOptionsMenu(menu)
    }

    private fun initReader(){
        fileReader = MyFileReader(this)
        binding.refreshFiles.apply {
            setOnRefreshListener {
                readFiles()
                isRefreshing = false;
            }
        }
        initFileView()
    }

    private fun initFileView() {
        fileAdapter = FileAdapter(readFileList,fileName)
        val mLayoutManager = GridLayoutManager(this, 2)
        binding.fileListView.apply {
            adapter = fileAdapter
            layoutManager = mLayoutManager
            addItemDecoration(
                SpacesItemDecoration(
                    2,
                    resources.getDimensionPixelSize(R.dimen.spacing),
                    false
                )
            )
        }

        fileAdapter!!.setOnItemShareListener(object : OnClickListener {
            override fun onClick(pos: Int) {
                Log.d("DebugAction:", "Selected $pos")
                readFileList[pos].selected = !readFileList[pos].selected
                shareFilesList.add(readFileList[pos].uri)
                fileAdapter?.notifyItemChanged(pos)
            }
        })
        readFiles()
    }

    /*---------------------------*/
    private fun readFiles(){
        asyncTask.execute(onPreExecute = {
            binding.fileReading.visibility = View.VISIBLE
        }, doInBackground = {
            fileReader?.readFiles()
        }, onPostExecute = {
            binding.fileReading.visibility = View.GONE
            it.apply {
                readFileList.clear()
                it?.forEachIndexed { index, file ->
                    readFileList.add(file)
                    fileAdapter?.notifyItemChanged(index)
                }
            }
        })
    }

    /*--- Share files ------*/
    private fun shareFile(){
        if(shareFilesList.size<1){
            return
        }
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, shareFilesList)
            type = "*/*"
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, null))
    }

    /*---------  File Writing ------------------------*/
    private fun initWriter(){
        createFileList()
        handler.postDelayed(dataRunnable,0)
    }

    private val dataRunnable = object: Runnable {
        override fun run() {
            val data = (Math.random()*100).toFloat()
            startWriting(data)
            handler.postDelayed(this, 0)
        }
    }

    private fun startWriting(data:Float){
        lifecycleScope.launch {
            writeData(data).buffer(1024).collect{
                if (isWriting){
                    fileWriter.writeDataInTheFile(it,writeFileList[0])
                }
            }
        }
    }

    /* Fusion */
    private fun writeData(data:Float): Flow<Float> {
        return flow{
            emit(data)
        }
    }

    private fun createFileList(){
        val dataArray = resources.getStringArray(R.array.data_types)
        dataArray.forEachIndexed { i, data ->
            val fileName = "Pain-"+ SimpleDateFormat("yyyy-MM-dd").format(
                Date()
            ) + "-$data.csv"

            Log.d("FileName","$fileName")
            val file = fileWriter.createFile(this,fileName)
            writeFileList.add(file!!)
        }
    }


    /*------ Runtime Permissions -------------------*/
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
                                android.R.string.cancel
                            ) { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.cancelPermissionRequest()
                            }
                            .setPositiveButton(
                                android.R.string.ok
                            ) { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.continuePermissionRequest()
                            }
                            .show()
                    }
                }
            ).check()
    }
}