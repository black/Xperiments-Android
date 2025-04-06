package com.awearsense.graphviewbarcharts

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphPlotters() {

    fun setSeries(
        context:Context,
        series: LineGraphSeries<DataPoint>,
        color: Int,
        thickness: Int
    ) {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = thickness.toFloat()
        paint.color = ContextCompat.getColor(context, color)
        series.setCustomPaint(paint)
        series.color = ContextCompat.getColor(context, color)
        series.thickness = thickness
//
    }

      fun setGraph(graph: GraphView, max: Int, graphName: String) {
//        graph.title = graphName
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(max.toDouble())
        graph.viewport.isScalable = true
//        graph.viewport.isYAxisBoundsManual = true
//        graph.viewport.isXAxisBoundsManual = true
        graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.HORIZONTAL
        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    // show normal x values
                    super.formatLabel(value, isValueX)
                } else {
                    // show currency for y values
                    if (value > 1000) {
                        super.formatLabel((value / 10000), isValueX) + " K"
                    } else {
                        super.formatLabel(value, isValueX)
                    }
                }
            }
        }
    }
}