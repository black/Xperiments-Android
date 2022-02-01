package com.black.xperiments.graphviewrecyclerview.graphs

interface OnItemClickListener {
    fun onItemClick(position:Int)
    fun onSwitchChange(position:Int,state:Boolean)
    fun onRangeSliderChange(position:Int,lower:Float,upper:Float)
}