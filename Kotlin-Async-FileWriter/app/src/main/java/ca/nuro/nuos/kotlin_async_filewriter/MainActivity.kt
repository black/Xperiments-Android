package ca.nuro.nuos.kotlin_async_filewriter

import android.Manifest
import android.content.Context
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ca.nuro.nuos.kotlin_async_filewriter.asyncversion.AsyncTask
import ca.nuro.nuos.kotlin_async_filewriter.asyncversion.doAsync
import ca.nuro.nuos.kotlin_async_filewriter.databinding.ActivityMainBinding
import ca.nuro.nuos.kotlin_async_filewriter.observers.SignalViewModel
import ca.nuro.nuos.kotlin_async_filewriter.sensor.DataInterface
import ca.nuro.nuos.kotlin_async_filewriter.sensor.FakeSensor
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val signalViewModel: SignalViewModel by viewModels()
    private val asyncTask: AsyncTask by lazy { ViewModelProvider(this)[AsyncTask::class.java] }

    private val fileList = arrayListOf<File>()
    private var isWriting = false
    private val sensor = FakeSensor()
    private val fileWriter = FileWriter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        validatePermission(permissions)

        sensor.startSensor()
        sensor.setDataListener(object :DataInterface{
            override fun onData(signal: Int) {
                signalViewModel.setFakeSignal(signal)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        observers()
        createFileNameList()
    }

    override fun onPause() {
        super.onPause()
        sensor.stopSensor()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_recorder)?.icon = ContextCompat.getDrawable(
            this,
            if(isWriting) R.drawable.ic_action_recorder_started else R.drawable.ic_action_recorder_stopped
        )

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_recorder -> {
                isWriting = !isWriting
                invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var t = 0
    private var m = 0
    private fun observers(){
        signalViewModel.getFakeSignal().observe(this){data->
            binding.sensorData.text = "$data $t   /  $m"
            t++;
            lifecycleScope.launch {
                signalViewModel.setSensorData(data).buffer().collect{
                    if (isWriting){
                        for (i in 0 until fileList.size){
                            fileWriter.writeDataInTheFile(it,fileList[i])
                        }
                    }
                    m++
                }
            }
        }

        signalViewModel.getFileName().observe(this){

        }
    }

    private fun writeFileOInBackground(){
        asyncTask.execute(onPreExecute = {
            // before

        }, doInBackground = {
            // background process

        }, onPostExecute = {
            // after
            // File(fileName).writeText(fileContent)
        })
    }

    /* File Write ---------*/
    private fun createFileNameList(){
        val algoSignalArray = resources.getStringArray(R.array.algoSignals)
        val eogSignalArray = resources.getStringArray(R.array.eogSignals)
        val eegSignalArray = resources.getStringArray(R.array.eegSignals)

        val allSignalArray = eogSignalArray.plus(eegSignalArray).plus(algoSignalArray)

        allSignalArray.forEachIndexed { i, signal ->
             val fileName = SimpleDateFormat("yyyy-MM-dd").format(
                 Date()
             ) + "-$signal.csv"

            val file = fileWriter.createFile(this,fileName)
            fileList.add(file!!)
         }
         // scanFile(applicationContext, File(mPath), "text/csv")
    }


    private fun scanFile(context: Context, f: File, mimeType: String) {
        MediaScannerConnection.scanFile(context, arrayOf(f.absolutePath), arrayOf(mimeType), null)
    }

    private fun writeFile(data: String, file: File?){
        doAsync {
           //   File(file).writeText(data)
        }.execute()
    }

    private fun writeFileRoutine() { // data: String, file: File?
//        asyncTask.execute(onPreExecute = {
//            isWriting = true
//            invalidateOptionsMenu()
//        }, doInBackground = {
//            // background process
//            // File(file).writeText(data)
//            // Thread.sleep(5000L)
//        }, onPostExecute = {
//            isWriting = false
//            invalidateOptionsMenu()
//            it.apply {
//               //  binding.resultsView.text = this
//            }
//        })

//        doAsync {
//            yourTask()
//        }.execute()
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