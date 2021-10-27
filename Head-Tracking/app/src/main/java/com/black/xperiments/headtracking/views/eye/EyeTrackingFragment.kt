package com.black.xperiments.headtracking.views.eye

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ca.nuro.nuos.mobile.extensions.SpacesItemDecoration
import com.black.xperiments.headtracking.R
import com.black.xperiments.headtracking.SensorViewModel
import com.black.xperiments.headtracking.databinding.FragmentEyeTrackingBinding
import com.black.xperiments.headtracking.extensions.GraphPlotters
import com.black.xperiments.headtracking.extensions.ViewBindingHolder
import com.black.xperiments.headtracking.extensions.ViewBindingHolderImpl
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class EyeTrackingFragment : Fragment(R.layout.fragment_eye_tracking),
    ViewBindingHolder<FragmentEyeTrackingBinding> by ViewBindingHolderImpl() {

    private var handler = Handler(Looper.getMainLooper())
    private val sensorViewModel: SensorViewModel by activityViewModels()
    private var eyeMove = "center"
    private var leftCount = 0
    private var rightCount = 0
    private var someCount = 0
    private var graphLastXValue = 5.0
    private var graphPlotters: GraphPlotters?=null
    private var eyeMovementDirection: EyeMovementDirection?=null
    private var leftStarted = false
    private var rightStarted = false

    /* Plotting*/
    private var leftEyeMove = LineGraphSeries<DataPoint>()
    private var rightEyeMove = LineGraphSeries<DataPoint>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentEyeTrackingBinding.inflate(layoutInflater), this) {
        graphPlotters = GraphPlotters(requireContext())
        observers()
        initGraphs()
    }

    private fun observers(){
        sensorViewModel.getEOGAllData().observe(viewLifecycleOwner,{
            if(!rightStarted) {
                if (!leftStarted && it.eyeMoveLeft > 0) {
                    leftStarted = !leftStarted
                    rightStarted = false
                }
            }

            if(!leftStarted) {
                if (!rightStarted && it.eyeMoveRight > 0) {
                    rightStarted = !rightStarted
                    leftStarted = false
                }
            }

            if(leftStarted || rightStarted){
                handler.postDelayed({
                    leftStarted = false
                    rightStarted = false
                }, 5000)
            }




//            eyeMovementDirection = EyeMovementDirection(it)
              leftEyeMove.appendData(DataPoint(graphLastXValue, it.eyeMoveLeft.toDouble()), true, 100)
              rightEyeMove.appendData(DataPoint(graphLastXValue, it.eyeMoveRight.toDouble()), true, 100)
              graphLastXValue += 1.0
//
//            if(it.eyeMoveLeft>0 || it.eyeMoveRight>0){
//                someCount = it.eyeMoveLeft-it.eyeMoveRight
//            }
            binding?.eyeMoveDirection?.text =  "Left: ${leftStarted} --- Right: ${rightStarted}"

        })
    }

    private fun initGraphs(){
        graphPlotters?.setSeries(leftEyeMove, R.color.brand, 3, R.color.brand)
        graphPlotters?.setSeries(rightEyeMove, R.color.black, 3, R.color.black)

        binding?.horzEyeMove?.apply {
            addSeries(leftEyeMove)
            addSeries(rightEyeMove)
            graphPlotters?.setGraph(binding.horzEyeMove, 100, "Eye Horz")
        }
    }


    companion object {
        fun newInstance() =
            EyeTrackingFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}