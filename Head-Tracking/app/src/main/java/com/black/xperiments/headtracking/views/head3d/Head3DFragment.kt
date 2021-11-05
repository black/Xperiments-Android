package com.black.xperiments.headtracking.views.head3d

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.black.xperiments.headtracking.R
import com.black.xperiments.headtracking.SensorViewModel
import com.black.xperiments.headtracking.databinding.FragmentHeadBinding
import com.black.xperiments.headtracking.databinding.FragmentHeadNavigationBinding
import com.black.xperiments.headtracking.extensions.GraphPlotters
import com.black.xperiments.headtracking.extensions.ViewBindingHolder
import com.black.xperiments.headtracking.extensions.ViewBindingHolderImpl
import com.black.xperiments.headtracking.views.headnav.HeadNav
import com.black.xperiments.headtracking.views.headnav.HeadNavigationFragment

class Head3DFragment : Fragment(R.layout.fragment_head),
    ViewBindingHolder<FragmentHeadBinding> by ViewBindingHolderImpl() {

    private val sensorViewModel: SensorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentHeadBinding.inflate(layoutInflater), this) {

    }

    companion object {
        fun newInstance() =
            Head3DFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}