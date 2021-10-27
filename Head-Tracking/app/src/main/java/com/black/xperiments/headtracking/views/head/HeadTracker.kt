package com.black.xperiments.headtracking.views.head

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
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

            Log.d(TAG,"P:${pitch} Y:${yaw} R:${roll}")
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.argb(0, 0, 0, 0))
        //get the position of the tip


        canvas?.let {
            val xp = r * cos(Math.toRadians(pitch.toDouble())) + l
            val yp = r * sin(Math.toRadians(pitch.toDouble())) + l
            drawPitch(it, xp.toFloat(), yp.toFloat())

            val xy = r * cos(Math.toRadians(yaw.toDouble()-30)) + l
            val yy = r * sin(Math.toRadians(yaw.toDouble()-30)) + l
            drawYaw(it, xy.toFloat(), yy.toFloat())

            val xr = r * cos(-Math.toRadians(roll.toDouble()+90)) + l
            val yr = r * sin(-Math.toRadians(roll.toDouble()+90)) + l
            drawRoll(it, xr.toFloat(), yr.toFloat())
        }




        invalidate()
    }



    private fun drawPitch(canvas: Canvas, x: Float, y: Float) {
        ellipse(canvas,l.toFloat(),y,15f,Color.BLACK)
        line(canvas, l.toFloat(),l.toFloat()-r, l.toFloat(), l.toFloat()+r)
    }

    private fun drawYaw(canvas: Canvas, x: Float, y: Float) {
        ellipse(canvas,x,l.toFloat(),15f,Color.BLACK)
        line(canvas, l.toFloat()-r,l.toFloat(), l.toFloat()+r, l.toFloat())
    }

    private fun drawRoll(canvas: Canvas, x: Float, y: Float) {
        ellipse(canvas,x,y,15f,Color.BLACK)
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