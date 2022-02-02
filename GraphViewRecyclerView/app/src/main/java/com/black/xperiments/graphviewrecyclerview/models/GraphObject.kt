package com.black.xperiments.graphviewrecyclerview.models

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

data class GraphObject(
    var title:String,
    var state:Boolean,
    var lower:Float,
    var upper:Float,
    var signalSeries: LineGraphSeries<DataPoint>,
    var lowerThresholdSeries: LineGraphSeries<DataPoint>,
    var upperThresholdSeries: LineGraphSeries<DataPoint>,
    var ticker:Double
)