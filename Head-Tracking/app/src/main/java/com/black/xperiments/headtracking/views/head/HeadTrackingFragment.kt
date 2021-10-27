package com.black.xperiments.headtracking.views.head

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.black.xperiments.headtracking.R
import com.black.xperiments.headtracking.SensorViewModel
import com.black.xperiments.headtracking.databinding.FragmentEyeTrackingBinding
import com.black.xperiments.headtracking.databinding.FragmentHeadTrackingBinding
import com.black.xperiments.headtracking.extensions.GraphPlotters
import com.black.xperiments.headtracking.extensions.ViewBindingHolder
import com.black.xperiments.headtracking.extensions.ViewBindingHolderImpl
import com.black.xperiments.headtracking.views.eye.EyeMovementDirection
import com.black.xperiments.headtracking.views.eye.EyeTrackingFragment
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class HeadTrackingFragment : Fragment(R.layout.fragment_head_tracking),
    ViewBindingHolder<FragmentHeadTrackingBinding> by ViewBindingHolderImpl() {

    private val sensorViewModel: SensorViewModel by activityViewModels()
    private var seriesAccX = LineGraphSeries<DataPoint>()
    private var seriesAccY = LineGraphSeries<DataPoint>()
    private var seriesAccZ = LineGraphSeries<DataPoint>()
    private var seriesPitch = LineGraphSeries<DataPoint>()
    private var seriesYaw = LineGraphSeries<DataPoint>()
    private var seriesRoll = LineGraphSeries<DataPoint>()
    private var graphLastXValue = 5.0
    private var graphPlotters:GraphPlotters?=null
    private var headMovementDirection: HeadMovementDirection?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentHeadTrackingBinding.inflate(layoutInflater), this) {
        graphPlotters = GraphPlotters(requireContext())
        observers()
        initGraphs()
        binding?.root?.post {
            val headTracker = HeadTracker(requireContext(), sensorViewModel)
            binding.headTrackerView.addView(headTracker)
        }
    }

    private fun observers(){
        sensorViewModel.getEOGAllData().observe(viewLifecycleOwner,{
            seriesAccX.appendData(DataPoint(graphLastXValue, it.accX.toDouble()), true, 100)
            seriesAccY.appendData(DataPoint(graphLastXValue, it.accY.toDouble()), true, 100)
            seriesAccZ.appendData(DataPoint(graphLastXValue, it.accZ.toDouble()), true, 100)
            seriesPitch.appendData(DataPoint(graphLastXValue, it.pitch.toDouble()), true, 100)
            seriesYaw.appendData(DataPoint(graphLastXValue, it.yaw.toDouble()), true, 100)
            seriesRoll.appendData(DataPoint(graphLastXValue, it.roll.toDouble()), true, 100)
            graphLastXValue += 1.0
        })
    }

    private fun initGraphs(){
        graphPlotters?.setSeries(seriesAccX, R.color.oneL, 3, R.color.one)
        graphPlotters?.setSeries(seriesAccY, R.color.one, 3, R.color.one)
        graphPlotters?.setSeries(seriesAccZ, R.color.twoL, 3, R.color.two)
        graphPlotters?.setSeries(seriesPitch, R.color.two, 3, R.color.two)
        graphPlotters?.setSeries(seriesYaw, R.color.threeL, 3, R.color.threeL)
        graphPlotters?.setSeries(seriesRoll, R.color.three, 3, R.color.three)


        binding?.accXPlot?.apply {
            addSeries(seriesAccX)
            graphPlotters?.setGraph(binding.accXPlot, 100, "Acc X")
        }

        binding?.accYPlot?.apply {
            addSeries(seriesAccY)
            graphPlotters?.setGraph(binding.accYPlot, 100, "Acc Y")
        }

        binding?.accZPlot?.apply {
            addSeries(seriesAccZ)
            graphPlotters?.setGraph(binding.accZPlot, 100, "Acc Z")
        }

        binding?.pitchPlot?.apply {
            addSeries(seriesPitch)
            graphPlotters?.setGraph(binding.pitchPlot, 100, "Pitch")
        }

        binding?.yawPlot?.apply {
            addSeries(seriesYaw)
            graphPlotters?.setGraph(binding.yawPlot, 100, "Yaw")
        }

        binding?.rollPlot?.apply {
            addSeries(seriesRoll)
            graphPlotters?.setGraph(binding.rollPlot, 100, "Roll")
        }

    }


    companion object {
        fun newInstance() =
            HeadTrackingFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}