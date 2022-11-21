package com.black.instadownloader.manager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.black.instadownloader.R

class InstaAdapter (private val respArray: ArrayList<InstaFile>) :
    RecyclerView.Adapter<InstaAdapter.ContentViewHolder>() {

    private var onItemClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.insta, parent, false)
        return onItemClickListener?.let { ContentViewHolder(itemView, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.fileUri.text = respArray[position].fileName
        holder.fileSize.text = "${respArray[position].fileSize} MB"
        holder.downloadStatus.text = if(respArray[position].dowloadStatus) "started" else "finished"
    }

    override fun getItemCount(): Int {
        return respArray.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val fileUri: TextView = itemView.findViewById(R.id.fileUri)
        val fileSize: TextView = itemView.findViewById(R.id.fileSize)
        val downloadStatus: TextView = itemView.findViewById(R.id.downloadStatus)
        val view: View = itemView
        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(adapterPosition)
                }
                Log.d("PressEvent", " Normal Pressed")
            }
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        onItemClickListener = listener
    }
}