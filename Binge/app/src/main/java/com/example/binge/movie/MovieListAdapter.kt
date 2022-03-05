package com.example.binge.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.R
import com.example.binge.common.GlideApp

class MovieListAdapter (private val context: Context, private val respArray: ArrayList<Movie>):
    RecyclerView.Adapter<MovieListAdapter.ContentViewHolder>(){

    private var onItemListener: OnItemSelected?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        return onItemListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = respArray[position]
        holder.title.text = currentItem.title
        holder.rating.rating = currentItem.rating.toFloat()

        GlideApp.with(context)
            .load(currentItem.img)
            .placeholder(R.drawable.loading)
            .error(R.drawable.ic_resource_default)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return respArray.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnItemSelected): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.movie_img)
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val rating: RatingBar = itemView.findViewById(R.id.movie_rating)
        val youTube: Button = itemView.findViewById(R.id.movie_youtube_url)

        init {
            itemView.setOnClickListener{
                val  position:Int = adapterPosition
                if (position!= RecyclerView.NO_POSITION){
                    listener.onItem(position)
                }
            }

            youTube.setOnClickListener {

            }
        }
    }

    fun setOnItemClickListener(listener: OnItemSelected) {
        onItemListener = listener
    }

}