package com.black.xperiments.headtracking.views.headnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.black.xperiments.headtracking.R
import com.black.xperiments.headtracking.SensorViewModel
import com.black.xperiments.headtracking.databinding.FragmentHeadNavigationBinding
import com.black.xperiments.headtracking.databinding.FragmentHeadTrackingBinding
import com.black.xperiments.headtracking.extensions.GraphPlotters
import com.black.xperiments.headtracking.extensions.ViewBindingHolder
import com.black.xperiments.headtracking.extensions.ViewBindingHolderImpl
import com.black.xperiments.headtracking.views.head.HeadTracker
import com.black.xperiments.headtracking.views.head.HeadTrackingFragment
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class HeadNavigationFragment : Fragment(R.layout.fragment_head_navigation),
    ViewBindingHolder<FragmentHeadNavigationBinding> by ViewBindingHolderImpl() {

    private val sensorViewModel: SensorViewModel by activityViewModels()
    private var graphPlotters: GraphPlotters?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentHeadNavigationBinding.inflate(layoutInflater), this) {
        graphPlotters = GraphPlotters(requireContext())
        headTrackerView.post {
            val headTracker = HeadNav(requireContext(), headTrackerView, sensorViewModel)
            headTrackerView.addView(headTracker)
        }
    }

    companion object {
        fun newInstance() =
            HeadNavigationFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}