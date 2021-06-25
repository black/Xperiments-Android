package com.black.edittextastextview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAssessAdapter(private val context: Context, private val respArray: Array<String>):
        RecyclerView.Adapter<RVAssessAdapter.ContentViewHolder>(){

    private var onItemClickListener:OnRVItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.response, parent, false)
        val height = (parent.height - 16) / 2
        view.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = respArray[position]
        holder.title.text = currentItem
    }

    override fun getItemCount(): Int {
        return respArray.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnRVItemClickListener):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.resp_text)
        val view = itemView

        init {
            itemView.setOnClickListener{
                val  position:Int = adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnRVItemClickListener) {
        onItemClickListener = listener
    }

}