package com.black.xperiments.graphviewrecyclerview.graphs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.black.xperiments.graphviewrecyclerview.models.GraphObject
import com.black.xperiments.graphviewrecyclerview.extensions.GraphPlotters
import com.black.xperiments.graphviewrecyclerview.R
import com.google.android.material.slider.RangeSlider
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint

class RecyclerViewGraphViewAdapter(
        private val context: Context,
        private val graphViewDataList: ArrayList<GraphObject>
) :
    RecyclerView.Adapter<RecyclerViewGraphViewAdapter.ContentViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var graphPlotters = GraphPlotters(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_graphview, parent, false)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = graphViewDataList[position]
        holder.titleView.text = currentItem.title
        holder.leftValue.text = currentItem.lower.toString().take(6)
        holder.rightValue.text = currentItem.upper.toString().take(6)
        holder.thresholdSeek.apply {
            values = listOf<Float>(currentItem.lower,currentItem.upper)
            addOnChangeListener { rangeSlider, value, fromUser ->
                holder.leftValue.text = rangeSlider.values[0].toString().take(6)
                holder.rightValue.text = rangeSlider.values[1].toString().take(6)
                onItemClickListener?.onRangeSliderChange(position, rangeSlider.values[0],rangeSlider.values[1])
            }
        }

        holder.switchView.isChecked  = currentItem.state

        graphPlotters.setSeries(currentItem.signalSeries, R.color.brand, 3, R.color.brand)
        graphPlotters.setSeries(currentItem.lowerThresholdSeries, R.color.positive, 3, R.color.positive)
        graphPlotters.setSeries(currentItem.upperThresholdSeries, R.color.positive, 3, R.color.positive)

        holder.graphView.apply {
            addSeries(currentItem.signalSeries) // signal
            addSeries(currentItem.lowerThresholdSeries) // lowerThreshold
            addSeries(currentItem.upperThresholdSeries) // upperThreshold
            graphPlotters.setGraph(this, 100, currentItem.title)
        }
    }

    override fun getItemCount(): Int {
        return graphViewDataList.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val graphView: GraphView = itemView.findViewById(R.id.graphView)
        val titleView: TextView = itemView.findViewById(R.id.graphTitle)
        val switchView: SwitchCompat = itemView.findViewById(R.id.graphSwitch)
        val leftValue: TextView = itemView.findViewById(R.id.lowerValue)
        val rightValue: TextView = itemView.findViewById(R.id.upperValue)
        val thresholdSeek: RangeSlider = itemView.findViewById(R.id.thresholdSeek)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }

            switchView.setOnCheckedChangeListener { buttonView, isChecked ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSwitchChange(position,isChecked)
                }
            }
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

}
