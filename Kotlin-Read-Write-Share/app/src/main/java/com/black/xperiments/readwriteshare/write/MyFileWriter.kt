package com.black.xperiments.readwriteshare.write

import android.content.Context
import android.os.Build
import android.os.Environment
import com.black.xperiments.readwriteshare.common.SensorData
import java.io.File
import java.io.FileOutputStream

class MyFileWriter{

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

    fun writeDataInTheFile(data:Float,file: File){
        if (file.exists()) {
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                writer.append(SensorData("$data").toCSVData())
                writer.close()
            }
        }
    }
}