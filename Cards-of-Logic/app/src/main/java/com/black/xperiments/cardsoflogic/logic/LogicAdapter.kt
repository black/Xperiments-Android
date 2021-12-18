package com.black.xperiments.cardsoflogic.logic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.black.xperiments.cardsoflogic.R

class LogicAdapter(
    private val context: Context,
    private val logicList: ArrayList<Cards>
):RecyclerView.Adapter<LogicAdapter.LogicViewHolder>(){

    private var onItemClickListener: OnCardClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogicViewHolder {
       val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_logic, parent, false)
       return LogicViewHolder(view, onItemClickListener!!)
    }

    override fun onBindViewHolder(holder: LogicViewHolder, position: Int) {
        logicList.let {
            holder.logicView.text = it[position].title
            holder.explanationView.text = it[position].explanation
        }
    }

    override fun getItemCount() = logicList.size

    inner class LogicViewHolder(itemView: View, listener: OnCardClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.logicImage)
        val logicView: TextView = itemView.findViewById(R.id.logic)
        val explanationView: TextView = itemView.findViewById(R.id.explanation)

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


    fun setOnItemClickListener(listener: OnCardClickListener) {
        onItemClickListener = listener
    }

////    inner class ProfileViewHolder(val binding: CardViewLogiBinding) :
////        RecyclerView.ViewHolder(binding.root)

}
