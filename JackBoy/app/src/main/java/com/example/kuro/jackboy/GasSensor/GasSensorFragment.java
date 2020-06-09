package com.example.kuro.jackboy.GasSensor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuro.jackboy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GasSensorFragment extends Fragment {


    public GasSensorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gas_sensor2, container, false);
    }

}
