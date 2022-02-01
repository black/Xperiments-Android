package com.black.xperiments.kotlinfilereadwrite

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class WriteFile(var context: Context) {
    private var TAG = "WriteFile"
    init {

    }

    // check if file exists ?ye
    fun checkDirectory(fileName:String): File? {
        var file = Environment.getExternalStorageDirectory()
        file = File(file,context.getString(R.string.app_name))

        return if(file.exists()){
                    file
                }else{
                    createFile(file, fileName)
                }
    }

    // create file if file doesn't exsits
    private fun createFile(file:File, fileName:String): File? {
        try {
            file.mkdir()
            return File(file,fileName)
        }catch (e:IOException){
            Log.d(TAG,e.toString())
            return null
        }
    }

    // write data in the file
    fun writeData(file:File, data:String){
        try {
            val fos = FileOutputStream(file)
            fos.write(data.toByteArray())
            fos.close()
        }catch (e:FileNotFoundException){
            Log.d(TAG,e.toString())
        }
        catch (e:IOException){
            Log.d(TAG,e.toString())
        }
    }
}