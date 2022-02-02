package com.black.xperiments.graphviewrecyclerview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.graphviewrecyclerview.databinding.FragmentBottomBinding
import com.black.xperiments.graphviewrecyclerview.extensions.ViewBindingHolder
import com.black.xperiments.graphviewrecyclerview.extensions.ViewBindingHolderImpl
import com.black.xperiments.graphviewrecyclerview.graphs.OnItemClickListener
import com.black.xperiments.graphviewrecyclerview.graphs.RecyclerViewGraphViewAdapter
import com.black.xperiments.graphviewrecyclerview.models.FusionStateData
import com.black.xperiments.graphviewrecyclerview.models.GraphObject
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class BottomFragment : DialogFragment(),
    ViewBindingHolder<FragmentBottomBinding> by ViewBindingHolderImpl() {

    private val TAG = "SignalFusion"
    private var rvGraphViewAdapter: RecyclerViewGraphViewAdapter?=null
    private var graphViewDataList  = arrayListOf<GraphObject>()

    private var someViewModel: SomeViewModel? = null
    private var fusionStateData = FusionStateData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentBottomBinding.inflate(layoutInflater), this) {

        rvGraphViewAdapter = RecyclerViewGraphViewAdapter(requireContext(),graphViewDataList)
        binding?.graphViewList?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = rvGraphViewAdapter
        }

        rvGraphViewAdapter?.setOnItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(TAG,"$position")
            }

            override fun onSwitchChange(position: Int, state: Boolean) {
                Log.d(TAG,"$position $state")
                fusionStateData.state[position] = state
                someViewModel?.setFusionState(fusionStateData)
            }

            override fun onRangeSliderChange(position: Int, lower: Float, upper: Float) {
                fusionStateData.threshold[position] = floatArrayOf(lower,upper)
                someViewModel?.setFusionState(fusionStateData)
            }

        })

        createListItems()
        presetValue()
        observers()
    }

    override fun onStart() {
        super.onStart()
        setupWidthToMatchParent()
    }

    private fun DialogFragment.setupWidthToMatchParent() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun presetValue(){
        graphViewDataList.forEachIndexed { i, graphObject ->
            graphObject.state = fusionStateData.state[i]
            graphObject.lower = fusionStateData.threshold[i][0]
            graphObject.upper = fusionStateData.threshold[i][1]
            graphObject.lowerThresholdSeries.appendData(
                DataPoint(graphObject.ticker, fusionStateData.threshold[i][0].toDouble()),
                true,
                100
            )
            graphObject.upperThresholdSeries.appendData(
                DataPoint(graphObject.ticker, fusionStateData.threshold[i][0].toDouble()),
                true,
                100
            )
            rvGraphViewAdapter?.notifyItemChanged(i)
        }
    }

    private fun createListItems(){
        for(i in 0..3) {
            val signal = LineGraphSeries<DataPoint>()
            val thresholdLower = LineGraphSeries<DataPoint>()
            val thresholdUpper = LineGraphSeries<DataPoint>()
            val ticker =  5.0
            graphViewDataList.add(
                GraphObject(
                    "signal $i",
                    false,
                    -50.0F,
                    50.0F,
                    signal,
                    thresholdLower,
                    thresholdUpper,
                    ticker
                )
            )
        }
    }

    private fun observers(){
        someViewModel?.getSensorData()?.observe(this){
            for(i in 0..3){
                if(
                    fusionStateData.state[i] &&
                    fusionStateData.threshold[i][0]<it[i] && it[i]<fusionStateData.threshold[i][1]
                ){
                    Log.d(TAG,"TRIG $i ${fusionStateData.state[i]} ${fusionStateData.threshold[i][0]} ${it[i]} ${fusionStateData.threshold[i][1]}")
                }
            }
        }

        someViewModel?.getSensorData()?.observe(this){

            graphViewDataList.forEachIndexed { i, graphObject ->

                // signal
                graphObject.signalSeries.appendData(
                    DataPoint(graphObject.ticker, it[i]),
                    true,
                    100
                )

                // lower threshold
                graphObject.lowerThresholdSeries.appendData(
                    DataPoint(graphObject.ticker, fusionStateData.threshold[i][0].toDouble()),
                    true,
                    100
                )

                // upper threshold
                graphObject.upperThresholdSeries.appendData(
                    DataPoint(graphObject.ticker, fusionStateData.threshold[i][1].toDouble()),
                    true,
                    100
                )
                graphObject.ticker += 1.0
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            listenerSignal: SomeViewModel,
            cacheState: FusionStateData
        ) =
            BottomFragment().apply {
                arguments = Bundle().apply {
                    someViewModel = listenerSignal
                    fusionStateData = cacheState
                }
            }
    }
}