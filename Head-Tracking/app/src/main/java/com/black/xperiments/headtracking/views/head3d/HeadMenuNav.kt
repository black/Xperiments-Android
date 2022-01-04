package com.black.xperiments.headtracking.views.head3d

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.black.xperiments.headtracking.SensorViewModel
import kotlin.math.cos
import kotlin.math.sin

class HeadMenuNav (
    respView: RecyclerView,
    context:Context,
    parent:RelativeLayout,
    var sensorViewModel: SensorViewModel
): View(context)  {
    private var respView: RecyclerView?=null
    private var l = 300
    private var r = 200
    private var pitch = 0.0f
    private var yaw = 0.0f
    private var roll = 0.0f
    private var rollThreshold = arrayListOf(0.0f,0.0f)
    private var pitchThreshold = arrayListOf(0.0f,0.0f)
    private var isSetThreshold = false
    private var colIndex:Float = 0.0f
    private var maxColumns  = 3
    private var rowIndex:Float = 0.0f
    private var maxRows = 3
    private var speedScan = 0.01f
    private var horizontalMargin = 10
    private var verticalMargin = 30
    private var clicked = false

    init {
        this.respView = respView
        l = parent.width/2
        sensorViewModel.getEOGAllData().observeForever{
            pitch = it.pitch
            yaw = it.yaw
            roll = it.roll
        }

        parent.setOnClickListener {
            isSetThreshold = true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawColor(Color.argb(0, 0, 0, 0))

            val rollPos = roll+90
            val pitchPos = pitch*4

            drawRoll(it,rollPos.toDouble(),Color.BLACK)
            drawPitch(it,rollPos.toDouble(), pitchPos.toDouble(),Color.BLACK)

            // set roll thresholds
            if(isSetThreshold){
                rollThreshold[0] = (rollPos)-horizontalMargin
                rollThreshold[1] = (rollPos)+horizontalMargin

                pitchThreshold[0] = pitchPos-verticalMargin
                pitchThreshold[1] = pitchPos+verticalMargin

                isSetThreshold = false
            }

            drawRoll(it,rollThreshold[0].toDouble(),Color.MAGENTA)
            drawRoll(it,rollThreshold[1].toDouble(),Color.MAGENTA)

            drawPitch(it,rollPos.toDouble(),pitchThreshold[0].toDouble(), Color.CYAN)
            drawPitch(it,rollPos.toDouble(),pitchThreshold[1].toDouble(), Color.CYAN)

            if(rollPos<rollThreshold[0]){
                // increase the col index value
                if(colIndex<maxColumns-1)colIndex+=speedScan
                else colIndex = maxColumns.toFloat()-1
            }

            if(rollThreshold[1]<rollPos){
                // decrease the col index value
                if(0<colIndex) colIndex-=speedScan
                else colIndex = 0.0f
            }

            if(pitchPos<pitchThreshold[0]){
                // decrease the row index value
                if(0<rowIndex)rowIndex-=speedScan
                else rowIndex = 0.0f
            }

            if(pitchThreshold[1]<pitchPos) {
                // increase the col index value
                if(rowIndex<maxRows-1)rowIndex+=speedScan
                else rowIndex = maxRows.toFloat()-1
            }

            val idx = colIndex.toInt() + rowIndex.toInt()*maxColumns
            sensorViewModel.setHighLightTileIndex(idx)

//            for(i in 0..8){
//                if (idx == i){
//                    highlightItem(idx, 1)
//                    if (clicked) {
//                        clicked = false
//                        triggerItem(0)
//                    }
//                }
//                else highlightItem(i, 0)
//            }
        }

        invalidate()
    }

    private fun drawPitch(canvas: Canvas,angRoll:Double,angPitch:Double,color: Int) {
        val x = (-angPitch * cos(-Math.toRadians(angRoll)) + l).toFloat()
        val y = (-angPitch * sin(-Math.toRadians(angRoll)) + l).toFloat()
        ellipse(canvas,x,y,15f,color)
    }


    private fun drawRoll(canvas: Canvas,ang:Double,color:Int) {
        val x = (r * cos(-Math.toRadians(ang)) + l).toFloat()
        val y = (r * sin(-Math.toRadians(ang)) + l).toFloat()
        ellipse(canvas,x,y,15f,color)
        line(canvas, l.toFloat(),l.toFloat(), x, y,color)
    }

    // draw Lines
    private fun line(canvas: Canvas, x: Float, y: Float, xx: Float, yy: Float,color:Int) {
        val line = Paint()
        line.isAntiAlias = true
        line.strokeWidth = 5f
        line.style = Paint.Style.STROKE
        line.color = color
        canvas.drawLine(x, y, xx, yy, line)
    }

    // draw Circles
    private fun ellipse(canvas: Canvas, x: Float, y: Float, r: Float, color: Int) {
        val ellipse = Paint()
        ellipse.isAntiAlias = true
        ellipse.style = Paint.Style.FILL
        ellipse.color = color
        val circle = RectF(x - r, y - r, x + r, y + r)
        canvas.drawOval(circle, ellipse)
    }

    // highlight
//    private fun highlightItem(position: Int, state: Int) {
//        respView?.getChildAt(position)?.apply{
//            isEnabled = state != 1
//            isPressed = state == 1
//        }
//    }

    // activate
//    private fun triggerItem(position: Int) {
//        respView?.apply {
//            getChildAt(position).isEnabled = true
//            findViewHolderForAdapterPosition(position)?.itemView?.performClick()
//        }
//    }


}