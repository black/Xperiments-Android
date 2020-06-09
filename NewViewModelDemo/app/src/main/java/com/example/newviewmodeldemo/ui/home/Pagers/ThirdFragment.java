package com.example.newviewmodeldemo.ui.home.Pagers;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newviewmodeldemo.DemoViewModels.DataViewModel;
import com.example.newviewmodeldemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

    private DataViewModel dataViewModel;
    private TextView textView;
    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(DataViewModel.class);
        textView = view.findViewById(R.id.thirdData);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void dataObservers(){
        dataViewModel.getCat().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("Fragment Third",integer+"");
                textView.setText(integer+"T");
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        dataObservers();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(dataViewModel!=null)
            dataViewModel.getCat().removeObservers(getViewLifecycleOwner());
    }
}
