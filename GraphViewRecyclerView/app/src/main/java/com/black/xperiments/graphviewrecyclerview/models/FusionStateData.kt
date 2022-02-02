package com.black.xperiments.graphviewrecyclerview.models

data class FusionStateData(
    var threshold:ArrayList<FloatArray> = arrayListOf(floatArrayOf(-50.0f,50.0f),floatArrayOf(-50.0f,50.0f),floatArrayOf(-50.0f,50.0f),floatArrayOf(-50.0f,50.0f)),
    var state:ArrayList<Boolean> = arrayListOf(false,false,false,false)
)
