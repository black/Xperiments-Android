package com.black.xperiments.bluetooth_server

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BluetoothDeviceAdapter(
    var  context: Context,
    var deviceList:ArrayList<BluetoothDevice>
):  RecyclerView.Adapter<BluetoothDeviceAdapter.ContentViewHolder>()  {

    private var onItemClickListener: OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.device_view, parent, false)
        return onItemClickListener?.let { ContentViewHolder(view, it) }!!
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val device = deviceList[position]
        holder.address.text = device.address
        holder.name.text = device.name
    }

    override fun getItemCount(): Int = deviceList.size


    inner class ContentViewHolder(itemView: View, listener: OnClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.deviceAddress)
        val name: TextView = itemView.findViewById(R.id.deviceName)
        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnClickListener) {
        onItemClickListener = listener
    }
}