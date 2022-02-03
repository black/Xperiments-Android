package com.black.xperiments.expandablerecyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TileAdapter (
    private val context: Context,
    tileLayout: Int,
    private val tileList: ArrayList<MyContent>
) :
    RecyclerView.Adapter<TileAdapter.ContentViewHolder>() {
    private var onTileClickListener: OnClickListener? = null
    private var tileLayout: Int = 0

    init {
        this.tileLayout = tileLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(tileLayout, parent, false)
        return onTileClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = tileList[position]
        holder.expandView.visibility = if(currentItem.expanded) View.VISIBLE else View.GONE
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.year.text = currentItem.year
        holder.rating.rating = currentItem.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return tileList.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val expandView: RelativeLayout = itemView.findViewById(R.id.expandableView)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val rating: RatingBar = itemView.findViewById(R.id.ratingbar)
        val year: TextView = itemView.findViewById(R.id.year)

       init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position)
                }
                Log.d("PressEvent", " Normal Pressed")
            }
        }
    }

    fun setOnTileClickListener(listener: OnClickListener) {
        onTileClickListener = listener
    }

}
