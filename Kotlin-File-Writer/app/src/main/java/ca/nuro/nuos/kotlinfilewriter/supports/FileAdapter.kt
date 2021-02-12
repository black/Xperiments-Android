package ca.nuro.nuos.kotlinfilewriter.supports


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.nuro.nuos.kotlinfilewriter.R
import ca.nuro.nuos.kotlinfilewriter.dataobjects.FileObj

class FileAdapter(private val respArray: ArrayList<FileObj>) :
    RecyclerView.Adapter<FileAdapter.ContentViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.file, parent, false)
        return ContentViewHolder(itemView, onItemClickListener!!)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.fileName.text = respArray[position].fileName
        holder.fileSize.text = respArray[position].fileSize
    }

    override fun getItemCount(): Int {
        return respArray.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val fileName: TextView = itemView.findViewById(R.id.fileName)
        val fileSize: TextView = itemView.findViewById(R.id.fileSize)
        private val share:ImageView = itemView.findViewById(R.id.shareFile)
        private val load:ImageView = itemView.findViewById(R.id.loadFile)
        init {
            val position: Int = adapterPosition
            share.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onShare(adapterPosition)
                }
            }
            load.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onLoad(adapterPosition)
                }
            }
        }
    }

    fun setOnItemShareListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }


}