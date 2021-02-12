package ca.nuro.nuos.kotlinfilewriter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private var writing:Boolean = false
    private var FILE_NAME:String = ""

    /*---Read Files-------*/
    private var fileList = ArrayList<FileObj>()
    private var fileAdapter: FileAdapter? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FILE_NAME = SimpleDateFormat("yyyy-MM-dd").format(
            Date()
        ) + "dummy.csv"
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
                println("DebugLoad $pos")
            }

            override fun onShare(pos: Int) {
                println("DebugShare $pos")
                shareFile(pos)
            }

        })

    }


    fun startWrite(view: View) {
        writing=!writing
        (view as Button).text = if (writing) "STOP" else "START"
        startWriting()
    }

    fun viewFile(view: View) {
        readRoutine()
    }

    private var i:Int = 0
    private fun startWriting(){
        handler.post(object : Runnable {
            override fun run() {
                binding.testData.text = i.toString()
                writeRoutine(i.toString())
                i += 1
                if (writing) {
                    handler.postDelayed(this, 100)
                } else {
                    handler.removeCallbacks(this)
                }
            }
        })
    }

    private fun writeRoutine(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            writeToFile(data)
        }
    }

    private fun writeToFile(data: String){
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE or MODE_APPEND)
            val bout = BufferedOutputStream(fos)
            bout.write(data.toByteArray())
            bout.close()
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readRoutine() {
        CoroutineScope(Dispatchers.IO).launch {
            readFiles()
            updateList()
        }
    }

    private fun readFiles(){
        val dir: File = filesDir
        val subFiles = dir.listFiles()
        if (subFiles!=null){
            for (file in subFiles){
                fileList.add(FileObj(file.name, (file.length() / (1024 * 1024)).toString() + " MB"))
            }
        }
    }

    private suspend fun updateList(){
        withContext(Dispatchers.Main) {
            fileAdapter!!.notifyDataSetChanged()
        }
    }



    fun shareFile(pos: Int) {
        getFileToShare(fileList[pos].fileName)
    }

    private fun getFileToShare(FILE_NAME: String?) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "*/*"
        val auth: String = getPackageName() + ".FileProvider"
        val uri = FileProvider.getUriForFile(
            this,
            auth,
            File(getFilesDir(), FILE_NAME)
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