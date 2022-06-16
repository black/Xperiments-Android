package com.black.xperiments.camera2x.camera

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView

class PreviewCamera2(context: Context):SurfaceView(context),SurfaceHolder.Callback2 {

    private var surfaceHolder:SurfaceHolder?=null
    private val paint = Paint()
    private var canvas = Canvas()

    init {
        surfaceHolder = holder
        surfaceHolder?.addCallback(this)
        // canvas = holder!!.lockCanvas()
    }

  /*  *//** Set the paint color and size. **//*
    private fun setPaint() {
        paint.color = Color.RED
        paint.isAntiAlias = true
    }*/

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {

    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(surfaceHolder: SurfaceHolder) {

    }
}