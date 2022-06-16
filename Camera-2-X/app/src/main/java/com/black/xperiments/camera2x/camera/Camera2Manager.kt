package com.black.xperiments.camera2x.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Process
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

class Camera2Manager(var context: Context) {

    /** A shape for extracting frame data. **/
    private val PREVIEW_WIDTH = 640
    private val PREVIEW_HEIGHT = 480
    /** The [android.util.Size] of camera preview.  */
    private var previewSize: Size? = null
    /** The [android.util.Size.getWidth] of camera preview. */
    private var previewWidth = 0
    /** The [android.util.Size.getHeight] of camera preview.  */
    private var previewHeight = 0
    /** An IntArray to save image data in ARGB8888 format  */
    private lateinit var rgbBytes: IntArray

    /** Orientation of the camera sensor.   */
    private var sensorOrientation: Int? = null
    private var imgDim: Size?=null

    private var cameraCaptureSession:CameraCaptureSession?=null
    private var captureRequest:CaptureRequest?=null
    private var cameraId: String = ""
    private var cameraDevice: CameraDevice? = null

    private val stateCallback: CameraDevice.StateCallback = object: CameraDevice.StateCallback(){
        override fun onOpened(cd: CameraDevice) {
            cameraDevice = cd
        }

        override fun onDisconnected(cd: CameraDevice) {
            cameraDevice?.apply {
                close()
            }
        }

        override fun onError(cd: CameraDevice, error: Int) {
            cameraDevice?.apply {
                close()
            }
            cameraDevice = null
        }
    }

    private val ORIENTATIONS = SparseIntArray()
    private var imageReader:ImageReader?=null

    init {
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    fun openCamera(){
        try {
            val manager =  context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraId = manager.cameraIdList[0]
            manager.openCamera(cameraId, stateCallback, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    fun setupCamera(){
      /*  try {
            val manager =  context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraId = manager.cameraIdList[0]
            manager.openCamera(cameraId, stateCallback, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }catch (e:NullPointerException){
            e.printStackTrace()
        }*/
    }

    fun closeCamera(){
        cameraDevice?.apply {
            close()
        }
        cameraDevice = null
    }

    private var imageAvailableListener = object :ImageReader.OnImageAvailableListener{
        override fun onImageAvailable(imageReader: ImageReader?) {
            // We need wait until we have some size from onPreviewSizeChosen
            if (previewWidth == 0 || previewHeight == 0) {
                return
            }

            val image = imageReader?.acquireLatestImage()?:return
        }
    }


}
