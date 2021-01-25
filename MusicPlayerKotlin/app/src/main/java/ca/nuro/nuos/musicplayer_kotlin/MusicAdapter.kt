package ca.nuro.nuos.musicplayer_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.nuro.nuos.musicplayer_kotlin.extensions.OnRVItemClickListener
import ca.nuro.nuos.musicplayer_kotlin.extensions.Song
import ca.nuro.nuos.musicplayer_kotlin.extensions.GlideApp

class MusicAdapter(private val context: Context, private val songList: List<Song>):
    RecyclerView.Adapter<MusicAdapter.ContentViewHolder>() {
    private var onItemClickListener: OnRVItemClickListener?=null

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
        GlideApp.with(context).load("").placeholder(R.drawable.roundcorner).into(holder.imageView)
//        holder.view.setBackgroundColor(if (songList[position].selected) Color.RED else Color.WHITE)
    }

    override fun getItemCount(): Int {
        return songList.size
    }


    inner class ContentViewHolder(itemView: View, listener: OnRVItemClickListener) : RecyclerView.ViewHolder(itemView)  {
        val view:View = itemView
        val imageView: ImageView = itemView.findViewById(R.id.cover)
        val title: TextView = itemView.findViewById(R.id.song_title)
        val artist: TextView = itemView.findViewById(R.id.song_artist)
        val duration: TextView = itemView.findViewById(R.id.song_duration)

        init {
            itemView.setOnClickListener{ v: View ->
                val  position:Int = adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    listener.OnItemClick(position)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnRVItemClickListener) {
        onItemClickListener = listener
    }


    private fun millisecondsToTime(milliseconds: Long): String? {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        val secondsStr = java.lang.Long.toString(seconds)
        val secs: String
        secs = if (secondsStr.length >= 2) {
            secondsStr.substring(0, 2)
        } else {
            "0$secondsStr"
        }
        return "$minutes:$secs"
    }
}