package com.black.xperiments.readwritefile.read

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.black.xperiments.readwritefile.common.MyFile
import java.io.File
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class MyFileReader(ctx: Context) {

    private var filePath:File?=null
    private var context:Context?=null

    init {
        context = ctx
        /*--- Check where to files from -----*/
        filePath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            context?.getExternalFilesDir(null)
        }else{
            Environment.getExternalStorageDirectory()
        }
    }

    fun readFiles():ArrayList<MyFile> {
        val tempFileList = arrayListOf<MyFile>()
        val dir: File? = filePath
        val subFiles = dir?.listFiles()
        subFiles?.forEachIndexed { index, file ->
            val fileURI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    context!!,
                    context?.packageName + ".provider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }
            tempFileList.add(MyFile(file.name, readableFileSize(file.length()),false,fileURI))
        }
        return tempFileList
    }

    private fun readableFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble()))
            .toString() + " " + units[digitGroups]
    }
}