package com.black.xperiments.music_read_kotlin

import android.Manifest
import android.content.DialogInterface
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.music_read_kotlin.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val asyncTask: AsyncTask by lazy { ViewModelProvider(this)[AsyncTask::class.java] }

    private var mediaPlayer: MediaPlayer?=null
    private var musicList = arrayListOf<Song>()
    private var musicAdapter: MusicAdapter? = null

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
        )
        validatePermission(permissions)

        /*-----Music player code----*/
        musicAdapter = MusicAdapter(this, musicList)
        binding.musicListView.post{
            binding.musicListView.adapter = musicAdapter
            binding.musicListView.layoutManager = LinearLayoutManager(this)
        }

        musicAdapter?.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(pos: Int) {
                musicList.forEachIndexed { index, song ->
                    song.selected = index==pos
                    musicAdapter?.notifyItemChanged(index)
                }
                playSelectedSong(musicList[pos])
            }
        })

        binding.playerAudio.playerPlayPause.setOnClickListener {
            playPause()
        }
    }

    private fun playSelectedSong(song: Song) {
        playerUI(true, R.drawable.ic_baseline_pause_circle_filled_24)
        resetPlayer()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(song.uri)
                prepare()
                binding.playerAudio.songname.text = song.title
                playerUI(true, R.drawable.ic_baseline_pause_circle_filled_24)
            }

            mediaPlayer?.apply {
                start()
                setOnCompletionListener {
                    playerUI(false, R.drawable.ic_baseline_play_arrow_24)
                }
            }
            initSeekBar()
        }catch (e:IOException){
            mediaPlayer?.release()
        }
    }

    override fun onStart() {
        super.onStart()
        getSongs()
    }

    //--------------------------
    override fun onPause() {
        super.onPause()
        playerUI(false, R.drawable.ic_baseline_play_arrow_24)
        resetPlayer()
    }

    private fun playerUI(state: Boolean, playerIcon: Int){
        binding.playerAudio.apply{
            playerPlayPause.setImageResource(playerIcon)
            songname.isSelected = state
        }
    }

    private fun playPause(){
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            playerUI(false, R.drawable.ic_baseline_play_arrow_24)
        }else{
            mediaPlayer?.start()
            playerUI(true, R.drawable.ic_baseline_pause_circle_filled_24)
        }
    }

    private fun resetPlayer() {
        mediaPlayer?.apply {
            stop()
            reset()
            release()
        }
        mediaPlayer=null
    }

    private fun initSeekBar(){
        binding.playerAudio.seekbarPlayer.max = mediaPlayer?.duration!!
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    playerUIValue(
                        mediaPlayer!!.currentPosition,
                        millisecondsToTime(mediaPlayer!!.currentPosition.toLong())
                    )
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    playerUI(false, R.drawable.ic_baseline_play_arrow_24)
                    playerUIValue(0, "00:00")
                    handler.removeCallbacks(this)
                }
            }
        }, 0)
    }

    private fun playerUIValue(seekProgress: Int, seektime: String){
        binding.playerAudio.apply {
            seekbarPlayer.progress = seekProgress
            playertime.text = seektime

        }
    }

    private fun millisecondsToTime(milliseconds: Long): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        val secondsStr = seconds.toString()
        val secs: String = if (secondsStr.length >= 2) {
            secondsStr.substring(0, 2)
        } else {
            "0$secondsStr"
        }
        return "$minutes:$secs"
    }


    /*---------------------------*/
    private fun getSongs(){
        asyncTask.execute(onPreExecute = {
            binding.fileReading.visibility = View.VISIBLE
        }, doInBackground = {
            loadSongs()
        }, onPostExecute = {
            binding.fileReading.visibility = View.GONE
            it.apply {
                musicList.clear()
                it.forEachIndexed { index, song ->
                    musicList.add(song)
                    musicAdapter?.notifyItemChanged(index)
                }
            }
        })
    }

    private fun loadSongs():ArrayList<Song>{
        val musicArraylist = arrayListOf<Song>()
        val projection = null
        val selection = MediaStore.Audio.Media.IS_MUSIC
        val selectionArgs = null
        val sortOrder = null

        applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            if(!cursor.moveToFirst())
                cursor.moveToFirst()

            do {
                val songTitle =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val songDuration =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val songArtist =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val songCover =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID))
                val songUri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                musicArraylist.add(
                    Song(
                        songCover, songTitle, songUri, songArtist, songDuration, false
                    )
                )
            } while (cursor.moveToNext())
        }
        return musicArraylist
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
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    p1?.continuePermissionRequest()
                                })
                            .show()
                    }
                }
            ).check()
    }


}