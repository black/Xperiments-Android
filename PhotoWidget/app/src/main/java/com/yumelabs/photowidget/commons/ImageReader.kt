package com.yumelabs.photowidget.commons

import android.content.Context
import android.provider.MediaStore
import java.lang.Exception

class ImageReader(var context:Context) {
    fun getAllImage():ArrayList<Picture>{
        val listOfImages = ArrayList<Picture>()
        val allImageURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA,MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = context.contentResolver.query(allImageURI,projection,null,null,null)
        try {
            if (cursor != null) {
                cursor.moveToFirst()
                do {
                    val img = Picture()
                    img.filePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    img.fileName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    listOfImages.add(img)
                } while (cursor.moveToNext())
                cursor.close()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return listOfImages
    }
}