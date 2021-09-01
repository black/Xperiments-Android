package com.yumelabs.bluetoothdatastreme.ui

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yumelabs.bluetoothdatastreme.R

class BLEDeviceAdapter(
    private val tileList: ArrayList<BluetoothDevice>
) :
    RecyclerView.Adapter<BLEDeviceAdapter.ContentViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.ble_device, parent, false)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val currentItem = tileList[position]
        holder.deviceInfo.text = "${currentItem.name}  :  ${currentItem.address}"
    }

    override fun getItemCount(): Int {
        return tileList.size
    }

    inner class ContentViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val deviceInfo: TextView = itemView.findViewById(R.id.deviceInfo)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }

            itemView.setOnLongClickListener { v: View ->
                val position: Int = adapterPosition
                listener.onLongItemClick(position)
                return@setOnLongClickListener true
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

}