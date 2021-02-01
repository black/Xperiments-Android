package ca.nuro.nuos.musicplayer_kotlin

import android.Manifest
import android.content.DialogInterface
import android.database.Cursor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ca.nuro.nuos.musicplayer_kotlin.databinding.ActivityMainBinding
import ca.nuro.nuos.musicplayer_kotlin.extensions.OnRVItemClickListener
import ca.nuro.nuos.musicplayer_kotlin.extensions.Song
import com.karumi.dexter.Dexter
import com.karumi.dexter.Dexter.withContext
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var mediaPlayer: MediaPlayer?=null
    private var musicList = arrayListOf<Song>()
    private var musicAdapter: MusicAdapter? = null
    private var currSong:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        validatePermission(permissions)

        musicAdapter = MusicAdapter(this,musicList)
        binding.musicListView.adapter = musicAdapter
        binding.musicListView.layoutManager = LinearLayoutManager(this)

        musicAdapter!!.setOnItemClickListener(object : OnRVItemClickListener {
            override fun OnItemClick(pos: Int) {
                if (pos == currSong && mediaPlayer != null) {
                    /* play pause same song*/
                    playPause()
                } else {
                    /* start a new song from the list*/
                    playContentUri(musicList.get(pos).uri, musicList.get(pos).title)
                    playerUI(true, R.drawable.ic_pause_circle_outline_24px)
                }
                currSong = pos
            }
        })

        binding.playerAudio.playpausePlayer.setOnClickListener {
            playPause()
        }

        loadSongs()

    }


    private fun playerUI(state: Boolean, playerIcon: Int){
        binding.playerAudio.playpausePlayer.setImageResource(playerIcon)
        binding.playerAudio.songname.isSelected = state
    }

    private fun playPause(){
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            playerUI(false, R.drawable.ic_play_circle_outline_24px)
        }else{
            mediaPlayer!!.start()
            playerUI(true, R.drawable.ic_pause_circle_outline_24px)
        }
    }


    /* ----------Media player methods---------- */

    fun playContentUri(uri: String, songName: String) {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(uri)
                prepare()
                binding.playerAudio.songname.text = songName
                playerUI(true, R.drawable.ic_pause_circle_outline_24px)
            }
            mediaPlayer!!.start()
            initSeekbar()
            mediaPlayer!!.setOnCompletionListener {
                playerUI(false, R.drawable.ic_play_circle_outline_24px)
            }
        } catch (e: IOException) {
            mediaPlayer = null
            mediaPlayer?.release()
        }
    }

    override fun onPause() {
        super.onPause()
        stopSound()
        playerUI(false, R.drawable.ic_play_circle_outline_24px)
    }

    private fun stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    private fun initSeekbar(){
        binding.playerAudio.seekbarPlayer.max = mediaPlayer!!.duration
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    playerUIValue( mediaPlayer!!.currentPosition,millisecondsToTime(mediaPlayer!!.currentPosition.toLong()))
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    playerUI(false, R.drawable.ic_play_circle_outline_24px)
                    playerUIValue(0,"00:00")
                    handler.removeCallbacks(this)
                }
            }
        }, 0)
    }

    private fun playerUIValue(seekProgress:Int, seektime:String){
        binding.playerAudio.seekbarPlayer.progress = seekProgress
        binding.playerAudio.playertime.text = seektime
    }

    private fun millisecondsToTime(milliseconds: Long): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        val secondsStr = seconds.toString()
        val secs: String
        secs = if (secondsStr.length >= 2) {
            secondsStr.substring(0, 2)
        } else {
            "0$secondsStr"
        }
        return "$minutes:$secs"
    }

    /*---------------------------*/
    private fun loadSongs() {
        CoroutineScope(Dispatchers.IO).launch {
            getSongs()
        }
    }

    private suspend fun refreshData() {
        withContext(Dispatchers.Main){
            runOnUiThread(Runnable {
                musicAdapter!!.notifyDataSetChanged()
            })
        }
    }

    private suspend fun getSongs() {
        musicList.clear()
        val songCursor: Cursor? = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Audio.Media.IS_MUSIC,
            null,
            null
        )

        while (songCursor != null && songCursor.moveToNext()) {
            val songTitle =
                songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songDuration =
                songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val songArtist =
                songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val songCover =
                songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))
            val songUri = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            println("SongURL " + songUri)
            val music = Song(
                songCover,
                songTitle,
                songUri,
                songArtist,
                songDuration,
                false
            )
            musicList.add(music)
        }
        refreshData()
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
                                android.R.string.cancel,
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    p1?.cancelPermissionRequest()
                                })
                            .setPositiveButton(
                                android.R.string.ok,
                                DialogInterface.OnClickListener { dialogInterface, _ ->
                                    dialogInterface.dismiss()
                                    p1?.continuePermissionRequest()
                                })
                            .show()
                    }
                }
            ).check()
    }
}