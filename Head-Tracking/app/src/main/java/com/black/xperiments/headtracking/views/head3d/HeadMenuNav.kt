package com.black.xperiments.headtracking.views.head3d

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
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
    sensorViewModel: SensorViewModel
): View(context)  {
    private var respView: RecyclerView?=null
    private var l = 300
    private var r = 200
    private var pitch = 0.0f
    private var yaw = 0.0f
    private var roll = 0.0f
    private var rollThreshold = arrayListOf(0.0f,0.0f,0.0f)
    private var pitchThreshold = arrayListOf(0.0f,0.0f,0.0f)
    private var clicked = false
    private var index = 0

    init {
        this.respView = respView
        l = parent.width/2
        sensorViewModel.getEOGAllData().observeForever{
            pitch = it.pitch
            yaw = it.yaw
            roll = it.roll
        }

        parent.setOnClickListener {
            clicked = true
            rollThreshold[0] = roll
            pitchThreshold[0] = pitch
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawColor(Color.argb(0, 0, 0, 0))
            drawRoll(it,roll.toDouble()+90,Color.BLACK)
            drawPitch(it,roll.toDouble()+90, pitch.toDouble()*2,Color.BLACK)

            if(clicked){
                drawRoll(it,rollThreshold[0].toDouble()+90,Color.MAGENTA)
                drawPitch(it,roll.toDouble()+90,pitchThreshold[0].toDouble()*4, Color.CYAN)
            }

            if(rollThreshold[0]<roll ){
                if(index<8)index++
                else index= 0
            }

            for(i in 0..9){
                if (index == i){
                    highlightItem(index, 1)
                }
                else highlightItem(i, 0)
            }
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
    private fun highlightItem(position: Int, state: Int) {
        respView?.getChildAt(position)?.apply{
            isEnabled = state != 1
            isPressed = state == 1
        }
    }

    // activate
    private fun triggerItem(position: Int) {
        respView?.apply {
            getChildAt(position).isEnabled = true
            findViewHolderForAdapterPosition(position)?.itemView?.performClick()
        }
    }


}