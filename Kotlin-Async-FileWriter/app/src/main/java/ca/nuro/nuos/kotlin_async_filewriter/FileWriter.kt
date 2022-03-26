package ca.nuro.nuos.kotlin_async_filewriter

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import ca.nuro.nuos.kotlin_async_filewriter.models.Signal
import java.io.File
import java.io.FileOutputStream

class FileWriter{

    fun createFile(context: Context,fileName:String): File? {
        val filePath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            context.getExternalFilesDir(null)?.absolutePath
        }else{
             Environment.getExternalStorageDirectory().absolutePath
        }


        if (!File(filePath).exists())
            File(filePath).mkdir()

        val file = File("$filePath",fileName)
        return if(file.exists()){
            file
        }else{
            if (file.createNewFile()){
                file
            }else null
        }
    }

    fun writeDataInTheFile(data:Int,file: File){
        if (file.exists()) {
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                writer.append(Signal("$data").toCSVData())
                writer.close()
            }
            Log.d("Write", "$data")
        }
    }
}