package com.black.xperiments.readwriteshare


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.black.xperiments.readwriteshare.common.MyFile
import com.black.xperiments.readwriteshare.common.OnClickListener

class FileAdapter(private val respArray: ArrayList<MyFile>,var currentFileName:String) :
    RecyclerView.Adapter<FileAdapter.ContentViewHolder>() {

    private var onItemClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.file, parent, false)
        return onItemClickListener?.let { ContentViewHolder(itemView, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.fileName.text = respArray[position].fileName
        holder.fileSize.text = respArray[position].fileSize
        if(respArray[position].fileName == currentFileName){
            holder.view.setBackgroundResource(R.drawable.rounded_rect_highlight)
        }
        holder.selectIcon.visibility = if(respArray[position].selected)View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return respArray.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val fileName: TextView = itemView.findViewById(R.id.fileName)
        val fileSize: TextView = itemView.findViewById(R.id.fileSize)
        val selectIcon: ImageView = itemView.findViewById(R.id.fileSelected)
        val view:View = itemView
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

    fun setOnItemShareListener(listener: OnClickListener) {
        onItemClickListener = listener
    }
}