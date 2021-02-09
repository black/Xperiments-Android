package ca.nuro.nuos.pix

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.nuro.nuos.musicplayer_kotlin.extensions.GlideApp
import ca.nuro.nuos.musicplayer_kotlin.extensions.OnRVItemClickListener
import ca.nuro.nuos.pix.extensions.Folder

class FolderAdapter (private val context: Context, private val folderList: List<Folder>):
        RecyclerView.Adapter<FolderAdapter.ContentViewHolder>() {
    private var onItemClickListener: OnRVItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pix, parent, false)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val imgsrc = folderList[position].uri
        val title = folderList[position].title
        val number = folderList[position].count
        holder.title.text = title
        GlideApp.with(context).load("").placeholder(R.drawable.ic_launcher_background).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }


    inner class ContentViewHolder(itemView: View, listener: OnRVItemClickListener) : RecyclerView.ViewHolder(itemView)  {
        val imageView: ImageView = itemView.findViewById(R.id.imgName)
        val title: TextView = itemView.findViewById(R.id.imgName)

        init {
            itemView.setOnClickListener{ v: View ->
                val  position:Int = adapterPosition
                if (position!= RecyclerView.NO_POSITION){
                    listener.OnItemClick(position)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnRVItemClickListener) {
        onItemClickListener = listener
    }

}