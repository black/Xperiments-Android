package com.example.sharepreferencenew.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager.widget.ViewPager;

import com.example.sharepreferencenew.DataRepository.DataViewModel;
import com.example.sharepreferencenew.DataRepository.SharedPrefManager;
import com.example.sharepreferencenew.DataRepository.StateObject;
import com.example.sharepreferencenew.R;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class HomeFragment extends Fragment{
    private static String TAG = "DEMOAPP_FRAG";
    private ViewPager viewPager;
    private DataViewModel dataViewModel;
    private StateObject stateObject;

    private int curr_level = 0 ;
    private SeekBar seekBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dataViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(DataViewModel.class);
        stateObject = SharedPrefManager.getInstance(getContext()).getStateObject();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seekBar = view.findViewById(R.id.m_seekbar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListner);

        viewPager = view.findViewById(R.id.interface_pager);
        ViewPagerAdapter myPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        viewPager.setCurrentItem(stateObject.getLevel());
        pageChangeListener.onPageSelected(stateObject.getLevel());

        stateObservers();
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            curr_level = position;
            dataViewModel.setLevel(curr_level);
            stateObject = SharedPrefManager.getInstance(getContext()).getStateObject();
            initFun();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFun();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser)dataViewModel.setSpeed(progress/10.0f);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void initFun(){
            switch (curr_level){
                case 0:
                    seekBar.setProgress((int)(stateObject.getSpeed_zero()*10));
                    break;
                case 1:
                    seekBar.setProgress((int)(stateObject.getSpeed_one()*10));
                    break;
                case 2:
                    seekBar.setProgress((int)(stateObject.getSpeed_two()*10));
                    break;
            }
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void stateObservers(){
        dataViewModel.getLevel().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                curr_level = val;
                stateObject.setLevel(val);
                SharedPrefManager.getInstance(getContext()).setStateObject(stateObject);
            }
        });

        dataViewModel.getSpeed().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float val) {
                switch (curr_level){
                    case 0:
                        stateObject.setSpeed_zero(val);
                        break;
                    case 1:
                        stateObject.setSpeed_one(val);
                        break;
                    case 2:
                        stateObject.setSpeed_two(val);
                        break;
                }

                SharedPrefManager.getInstance(getContext()).setStateObject(stateObject);
            }
        });
    }
}
