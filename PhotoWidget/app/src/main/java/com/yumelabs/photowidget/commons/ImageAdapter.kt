package com.yumelabs.photowidget.commons

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yumelabs.photowidget.R
import com.yumelabs.photowidget.extensions.GlideApp
import java.io.IOException

class ImageAdapter(
    private val context: Context,
    tileLayout: Int,
    private val imageList: ArrayList<Picture>
) :
    RecyclerView.Adapter<ImageAdapter.ContentViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var tileLayout: Int = 0

    init {
        this.tileLayout = tileLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(tileLayout, parent, false)
        return onItemClickListener?.let { ContentViewHolder(itemView, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.title.text = currentItem.fileName
        try {
            GlideApp.with(context)
                .load(currentItem.filePath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.imageView)
        }catch (e: IOException){
            println("$e")
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.fileName)
        val imageView: ImageView = itemView.findViewById(R.id.file)
        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
                Log.d("PressEvent", " Normal Pressed")
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

}