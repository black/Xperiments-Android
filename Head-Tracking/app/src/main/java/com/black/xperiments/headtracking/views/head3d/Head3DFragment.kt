package com.black.xperiments.headtracking.views.head3d

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ca.nuro.nuos.mobile.extensions.SpacesItemDecoration
import ca.nuro.nuos.standard.common.OnRVItemClickListener
import com.black.xperiments.headtracking.R
import com.black.xperiments.headtracking.SensorViewModel
import com.black.xperiments.headtracking.common.FakeTile
import com.black.xperiments.headtracking.common.RVTileAdapter
import com.black.xperiments.headtracking.databinding.FragmentHeadBinding
import com.black.xperiments.headtracking.extensions.GraphPlotters
import com.black.xperiments.headtracking.extensions.ViewBindingHolder
import com.black.xperiments.headtracking.extensions.ViewBindingHolderImpl
import com.black.xperiments.headtracking.views.headnav.HeadNav
import com.black.xperiments.headtracking.views.headnav.HeadNavigationFragment

class Head3DFragment : Fragment(R.layout.fragment_head),
    ViewBindingHolder<FragmentHeadBinding> by ViewBindingHolderImpl() {

    private val sensorViewModel: SensorViewModel by activityViewModels()
    private var rvAdapter: RVTileAdapter?=null
    private var tileList = arrayListOf<FakeTile>()
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentHeadBinding.inflate(layoutInflater), this) {
        rvAdapter = RVTileAdapter(requireContext(), 3, 24, R.layout.tile_intermediate, tileList)
        binding?.responseView?.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(
                SpacesItemDecoration(
                    3,
                    resources.getDimensionPixelSize(R.dimen.spacing),
                    false
                )
            )
        }

        headTrackerView.post {
            val headTracker = HeadMenuNav(binding?.responseView!!,requireContext(), headTrackerView, sensorViewModel)
            headTrackerView.addView(headTracker)
        }

        rvAdapter?.setOnItemClickListener(object : OnRVItemClickListener {
            override fun onItemClick(pos: Int) {
                if (tileList.size > 0) {
                    sensorViewModel.setMessage(tileList[pos].description)
                }
            }
            override fun onLongItemClick(pos: Int) {
                Toast.makeText(context,"Intermediate Long Press", Toast.LENGTH_LONG).show()
            }
        })

        createFakeMenu()
        observers()
    }

    private fun observers(){
        sensorViewModel.getTrigger().observeForever{
            if(it=="head")clicked=true
        }

        sensorViewModel.getHighLightTileIndex().observe(viewLifecycleOwner){
            for(i in 0..tileList.size){
                if (it == i){
                    highlightItem(it, 1)
                    if (clicked) {
                        clicked = false
                        if (tileList.size > 0) {
                            sensorViewModel.setMessage(tileList[it].description)
                        }
                    }
                }
                else highlightItem(i, 0)
            }
        }
    }

    private fun highlightItem(position: Int, state: Int) {
        binding?.responseView?.getChildAt(position)?.apply{
            isEnabled = state != 1
            isPressed = state == 1
        }
    }

    private fun createFakeMenu(){
        for (i in 0..9) {
            tileList.add(FakeTile("${i} title","${i} des"))
          }
        rvAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() =
            Head3DFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}