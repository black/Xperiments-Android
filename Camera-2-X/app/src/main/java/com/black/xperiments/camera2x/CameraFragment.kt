package com.black.xperiments.camera2x

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.araujo.jordan.excuseme.ExcuseMe
import com.black.xperiments.camera2x.camera.Camera2Manager
import com.black.xperiments.camera2x.databinding.FragmentCameraBinding
import com.black.xperiments.livetextsearch.ViewBindingHolder
import com.black.xperiments.livetextsearch.ViewBindingHolderImpl

class CameraFragment : Fragment(R.layout.fragment_camera),
    ViewBindingHolder<FragmentCameraBinding> by ViewBindingHolderImpl() {

    private var camera2Manager = Camera2Manager(requireContext())
    private var surfaceHolder: SurfaceHolder? = null
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentCameraBinding.inflate(layoutInflater), this)  {
        ExcuseMe.couldYouGive(requireActivity()).gently(
            "Camera Permission Request",
            "To see the live text search with your camera"
        ).permissionFor(Manifest.permission.CAMERA){
            camera2Manager.openCamera()
        }

        surfaceHolder = binding?.cameraPreview?
    }

    override fun onPause() {
        super.onPause()
        camera2Manager.closeCamera()
    }

}