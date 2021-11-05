package com.black.xperiments.headtracking.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.black.xperiments.headtracking.views.eye.EyeTrackingFragment
import com.black.xperiments.headtracking.views.head.HeadTrackingFragment
import com.black.xperiments.headtracking.views.head3d.Head3DFragment
import com.black.xperiments.headtracking.views.headnav.HeadNavigationFragment

class ViewPagerAdapter(manager: FragmentManager): FragmentStatePagerAdapter(
    manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
){
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> EyeTrackingFragment.newInstance()
            1 -> HeadTrackingFragment.newInstance()
            2 -> HeadNavigationFragment.newInstance()
            3 -> Head3DFragment.newInstance()
            else -> EyeTrackingFragment.newInstance()
        }
    }
    override fun getCount() = 4
}