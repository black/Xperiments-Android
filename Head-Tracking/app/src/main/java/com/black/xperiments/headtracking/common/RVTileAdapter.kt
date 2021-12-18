package com.black.xperiments.headtracking.common

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.nuro.nuos.standard.common.OnRVItemClickListener
import com.black.xperiments.headtracking.R
import java.io.IOException

class RVTileAdapter(
        private val context: Context,
        rowCount: Int,
        itemSpacing: Int,
        tileLayout: Int,
        private val tileList: ArrayList<FakeTile>
) :
    RecyclerView.Adapter<RVTileAdapter.ContentViewHolder>() {
    private var onItemClickListener: OnRVItemClickListener? = null
    private var rowCount: Int = 0
    private var itemSpacing: Int = 0
    private var tileLayout: Int = 0

    init {
        this.rowCount = rowCount
        this.itemSpacing = itemSpacing
        this.tileLayout = tileLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(tileLayout, parent, false)
        val height = (parent.measuredHeight - itemSpacing) / rowCount
        view.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = tileList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.imageView.setImageResource(
                context.resources.getIdentifier(
                        "resp_$currentItem",
                        "drawable",
                        context.packageName
                )
        )

    }

    override fun getItemCount(): Int {
        return tileList.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnRVItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.resp_img)
        val moreView: ImageView = itemView.findViewById(R.id.resp_more)
        val title: TextView = itemView.findViewById(R.id.resp_title)
        val description: TextView = itemView.findViewById(R.id.resp_description)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
                Log.d("PressEvent", " Normal Pressed")
            }

            itemView.setOnLongClickListener { v: View ->
                val position: Int = adapterPosition
                listener.onLongItemClick(position)
                Log.d("PressEvent", " Long Pressed")
                return@setOnLongClickListener true
            }
        }
    }

    fun setOnItemClickListener(listener: OnRVItemClickListener) {
        onItemClickListener = listener
    }

}
