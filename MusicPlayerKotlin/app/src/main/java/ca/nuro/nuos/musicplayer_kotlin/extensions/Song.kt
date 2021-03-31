package ca.nuro.nuos.musicplayer_kotlin.extensions

import android.net.Uri
import java.net.URI

data class Song(val img:String, val title:String, val uri: Uri, val artist:String, val duration:Long,val selected:Boolean)