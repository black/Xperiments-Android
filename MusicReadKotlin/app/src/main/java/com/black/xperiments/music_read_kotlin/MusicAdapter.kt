package com.black.xperiments.music_read_kotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private val context: Context, private val songList: List<Song>):
    RecyclerView.Adapter<MusicAdapter.ContentViewHolder>() {
    private var onItemClickListener: OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.music, parent, false)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val imgsrc = songList[position].img
        val title = songList[position].title
        val artist = songList[position].artist
        val duration = millisecondsToTime(songList[position].duration)
        holder.title.text = title
        holder.artist.text = artist
        holder.duration.text = duration.toString()
        Log.d("SongBug","$position ${songList[position].selected}")
        holder.view.background = ContextCompat.getDrawable(context, if(songList[position].selected) R.drawable.selected_item else R.drawable.roundcorner_playlist)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView)  {
        var view = itemView
        val imageView: ImageView = itemView.findViewById(R.id.cover)
        val title: TextView = itemView.findViewById(R.id.song_title)
        val artist: TextView = itemView.findViewById(R.id.song_artist)
        val duration: TextView = itemView.findViewById(R.id.song_duration)

        init {
            itemView.setOnClickListener{ v: View ->
                val  position:Int = adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    private fun millisecondsToTime(milliseconds: Long): String? {
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
}