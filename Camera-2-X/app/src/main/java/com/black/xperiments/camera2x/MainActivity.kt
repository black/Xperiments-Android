package com.black.xperiments.camera2x

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, CameraFragment())
            .commit()
    }
}


/**
 * References CameraX and Camera2 API
 * https://github.com/hamza372/FramesOfLiveFeedKotlin/blob/main/app/src/main/java/com/example/livecamerafootagekotlin/CameraConnectionFragment.kt
 * https://github.com/hamza372/FramesOfLiveFeedKotlin/blob/main/app/src/main/java/com/example/livecamerafootagekotlin/ImageUtils.kt
 * https://hamzaasif-mobileml.medium.com/getting-frames-of-live-camera-footage-as-bitmaps-in-android-using-camera2-api-kotlin-40ba8d3afc76
 * https://hamzaasif-mobileml.medium.com/getting-frames-of-live-camera-footage-in-android-using-camera2-api-52cf4437f5fd
 * https://hamzaasif-mobileml.medium.com/android-capturing-images-from-camera-or-gallery-as-bitmaps-kotlin-50e9dc3e7bd3
 * https://www.youtube.com/watch?v=HjXJh_vHXFs
 */