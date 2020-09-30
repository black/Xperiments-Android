package com.experiments.vuemodelwithvuepager.Pages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.experiments.vuemodelwithvuepager.R;
import com.experiments.vuemodelwithvuepager.VueModel;

public class ThirdFragment extends Fragment {

    private VueModel vueModel;
    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vueModel = new ViewModelProvider(getActivity()).get(VueModel.class);
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = view.findViewById(R.id.third_list);
        vueModel.getData().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                tv.setText("");
                for(String msg:strings){
                    tv.append(msg);
                }
            }
        });
    }
}