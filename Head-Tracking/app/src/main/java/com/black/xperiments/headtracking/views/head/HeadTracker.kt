package com.black.xperiments.headtracking.views.head

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import com.black.xperiments.headtracking.SensorViewModel
import kotlin.math.cos
import kotlin.math.sin

class HeadTracker (
    context:Context,
    sensorViewModel: SensorViewModel
): View(context)  {
    private val TAG = "SENSORHEAD"
    private var l = 300
    private var r = 200
    private var pitch = 0.0f
    private var yaw = 0.0f
    private var roll = 0.0f

    init {
        sensorViewModel.getEOGAllData().observeForever{
            pitch = it.pitch
            yaw = it.yaw
            roll = it.roll
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            it.drawColor(Color.argb(0, 0, 0, 0))
            drawPitch(it,pitch.toDouble()*2,Color.BLACK)
            drawYaw(it, yaw.toDouble()*2,Color.BLACK)
            drawRoll(it,roll.toDouble()+90,Color.BLACK)
        }
        invalidate()
    }

    private fun drawPitch(canvas: Canvas,ang:Double,color: Int) {
        val x = (r * cos(Math.toRadians(ang)) + l).toFloat()
        val y = (r * sin(Math.toRadians(ang)) + l).toFloat()
        ellipse(canvas, l.toFloat(),y,15f,color)
        line(canvas, l.toFloat(),l.toFloat()-r, l.toFloat(), l.toFloat()+r)
    }

    private fun drawYaw(canvas: Canvas,ang:Double,color: Int) {
        val x = (r * cos(Math.toRadians(ang)) + l).toFloat()
        val y = (r * sin(Math.toRadians(ang)) + l).toFloat()
        ellipse(canvas,x,l.toFloat(),15f,color)
        line(canvas, l.toFloat()-r,l.toFloat(), l.toFloat()+r, l.toFloat())
    }

    private fun drawRoll(canvas: Canvas,ang:Double,color:Int) {
        val x = (r * cos(-Math.toRadians(ang)) + l).toFloat()
        val y = (r * sin(-Math.toRadians(ang)) + l).toFloat()
        ellipse(canvas,x,y,15f,color)
        line(canvas, l.toFloat(),l.toFloat(), x, y)
    }

    // draw Lines
    private fun line(canvas: Canvas, x: Float, y: Float, xx: Float, yy: Float) {
        val line = Paint()
        line.isAntiAlias = true
        line.strokeWidth = 5f
        line.style = Paint.Style.STROKE
        line.color = Color.BLACK
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


}