package com.awearsense.graphviewbarcharts

import com.awearsense.graphviewbarcharts.Constants.BUFFERSIZE
import com.awearsense.graphviewbarcharts.Constants.HOPSIZE
import com.github.psambit9791.jdsp.transform.FastFourier
import com.github.psambit9791.jdsp.windows.Hanning

class RealtimeFFT{
    private val window = Hanning(BUFFERSIZE,true).window
    private val segment = DoubleArray(BUFFERSIZE)
    private var bufferIndex = 0
    private var result = DoubleArray(BUFFERSIZE)

    fun processSample(sample: Double): DoubleArray {
        segment[bufferIndex] = sample * window[bufferIndex]
        bufferIndex++
        if (bufferIndex == BUFFERSIZE) {
           result = performFFT(segment)
            shiftBuffer()
        }
        return result
    }

    private fun performFFT(segment:DoubleArray): DoubleArray {
       val fft = FastFourier(segment)
        fft.transform()
        return fft.getMagnitude(true)
    }

    private fun shiftBuffer() {
        segment.copyInto(segment, 0, HOPSIZE)
        bufferIndex = BUFFERSIZE - HOPSIZE
    }
}