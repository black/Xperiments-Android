package com.example.viewpagerwithdynamicviews.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.viewpagerwithdynamicviews.PageAdapter;
import com.example.viewpagerwithdynamicviews.PagerModel;
import com.example.viewpagerwithdynamicviews.R;
import com.example.viewpagerwithdynamicviews.VueModel;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class SecondFragment extends Fragment {

    private VueModel vueModel;
    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vueModel = new ViewModelProvider(getActivity()).get(VueModel.class);
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = view.findViewById(R.id.newFragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Action","Clicked");

                vueModel.setPosition(new PagerModel(1,true));
            }
        });

    }
}