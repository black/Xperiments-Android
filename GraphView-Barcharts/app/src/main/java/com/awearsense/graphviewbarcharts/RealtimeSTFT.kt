package com.awearsense.graphviewbarcharts

import com.awearsense.graphviewbarcharts.Constants.BUFFERSIZE
import com.awearsense.graphviewbarcharts.Constants.HOPSIZE
import com.github.psambit9791.jdsp.transform.ShortTimeFourier

class RealtimeSTFT {

    fun performSTFT(signal:DoubleArray): Array<out DoubleArray>? {
        val stft = ShortTimeFourier(signal, BUFFERSIZE, HOPSIZE)
        stft.transform()
        return stft.getMagnitude(true)
    }
}