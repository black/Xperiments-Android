package com.black.xperiments.graphviewrecyclerview.models

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

data class GraphObject(
    var title:String,
    var state:Boolean,
    var signalSeries: ArrayList<LineGraphSeries<DataPoint>>)