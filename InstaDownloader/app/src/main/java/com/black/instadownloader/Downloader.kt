package com.black.instadownloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.File


class Downloader(var ctx:Context) {
    // download from url captured from the edittext
     fun getContent(uri: String):String{
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(ctx)
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, uri,
            { response ->
                Log.d("Download-Info","Response is -->: $response")
            },
            {
                Log.d("Download-Info","That didn't work!")
            }
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        return ""
    }

    fun saveToStorage(url: String,fileName:String){
        try {
            val downloadManager = ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val image_url = Uri.parse(url)
            val request = DownloadManager.Request(image_url)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("image/*,video/*")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+fileName+"."+getMimeType(url))
            downloadManager.enqueue(request)
        }catch (e:Exception){
            Log.e("Download","Failed $e")
        }
    }

    private fun getMimeType(url: String?): String? {
        return MimeTypeMap.getFileExtensionFromUrl(url)
    }
}