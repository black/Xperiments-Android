package ca.nuro.nuos.kotlinfilewriter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ca.nuro.nuos.kotlinfilewriter.databinding.ActivityMainBinding
import ca.nuro.nuos.kotlinfilewriter.dataobjects.FileObj
import ca.nuro.nuos.kotlinfilewriter.supports.FileAdapter
import ca.nuro.nuos.kotlinfilewriter.supports.OnItemClickListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private var writing: Boolean = false
    private var FILE_NAME: String = ""
    private var file: File? = null
    private var fileDir: File? = null
    private var mDir = Environment.DIRECTORY_DOCUMENTS + "/Nuro/"
    private var mPath = Environment.getExternalStoragePublicDirectory(mDir)

    /*---Read Files-------*/
    private var fileList = ArrayList<FileObj>()
    private var fileAdapter: FileAdapter? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FILE_NAME = SimpleDateFormat("yyyyMMddHHmm").format(
                Date()
        ) + "dummy.csv"


        with(mPath) {
            fileDir = File(this.absolutePath)
            with(fileDir) {
                if (!this!!.exists())
                    this.mkdir()
            }
        }

        /*Permissions*/
        val permissions = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
        validatePermission(permissions)
        fileAdapter = FileAdapter(fileList)
        binding.fileListView.adapter = fileAdapter
        binding.fileListView.layoutManager = LinearLayoutManager(this)
        fileAdapter!!.setOnItemShareListener(object : OnItemClickListener {
            override fun onLoad(pos: Int) {
                Log.d("DebugAction:", "Load $pos")
            }

            override fun onShare(pos: Int) {
                Log.d("DebugAction:", "Share $pos")
                shareFile(pos)
            }

        })

    }

    private fun writeOnFile(data: String) {
        try {
            BufferedWriter(FileWriter(file, true)).use {
                it.write(data)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return
        }
    }


    fun startWrite(view: View) {
        writing = !writing
        (view as Button).text = if (writing) "STOP" else "START"

        file = File(fileDir, FILE_NAME)
        file?.createNewFile()

        startWriting()
    }

    fun viewFile(view: View) {
        readRoutine()
        fileList.clear()
        fileAdapter?.notifyDataSetChanged()
    }

    private var i: Int = 0
    private fun startWriting() {
        handler.post(object : Runnable {
            override fun run() {
                binding.testData.text = "Data: $i"
                writeRoutine("Data: $i")
                i += 1
                if (writing) {
                    handler.postDelayed(this, 10)
                } else {
                    handler.removeCallbacks(this)
                }
                scanFile(this@MainActivity, File(mPath.absolutePath), "text/csv")
            }
        })
    }

    private fun writeRoutine(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            writeOnFile(data)
        }
    }

    private fun scanFile(context: Context, f: File, mimeType: String) {
        MediaScannerConnection.scanFile(context, arrayOf(f.absolutePath), arrayOf(mimeType), null)
    }

    private fun readRoutine() {
        CoroutineScope(Dispatchers.IO).launch {
            readFiles()
            updateList()
        }
    }

    private fun readFiles() {
        val dir: File = mPath
        val subFiles = dir.listFiles()
        if (subFiles != null) {
            for (item in subFiles) {
                fileList.add(FileObj(item.name, readableFileSize(item.length())))
            }
        }
    }

    fun readableFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())).toString() + " " + units[digitGroups]
    }

    private suspend fun updateList() {
        withContext(Dispatchers.Main) {
            fileAdapter!!.notifyDataSetChanged()
        }
    }


    fun shareFile(pos: Int) {
        getFileToShare(fileList[pos].fileName)
    }

    private fun getFileToShare(fileName: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "*/*"
        val auth = "$packageName.FileProvider"
        val uri = FileProvider.getUriForFile(
                this,
                auth,
                File(filesDir, fileName)
        )
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
        sharingIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(sharingIntent, "Share SENSOR Data"))
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