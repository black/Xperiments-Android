package com.black.xperiments.camera2x.camera

import android.view.SurfaceHolder

interface VueState {
    fun onViewState(string: String, surfaceHolder: SurfaceHolder)
}