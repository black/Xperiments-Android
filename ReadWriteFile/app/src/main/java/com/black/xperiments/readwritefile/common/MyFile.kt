package com.black.xperiments.readwritefile.common

import android.net.Uri

data class MyFile (var fileName:String, var fileSize:String, var selected:Boolean, var uri: Uri)