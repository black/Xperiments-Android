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

import com.example.viewpagerwithdynamicviews.PagerModel;
import com.example.viewpagerwithdynamicviews.R;
import com.example.viewpagerwithdynamicviews.VueModel;

public class ThridFragment extends Fragment {

    private VueModel vueModel;
    public ThridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vueModel = new ViewModelProvider(getActivity()).get(VueModel.class);
        return inflater.inflate(R.layout.fragment_thrid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = view.findViewById(R.id.closeFragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Action","Clicked");
                int page = vueModel.getPosition().getValue().getPageNumber();
                vueModel.setPosition(new PagerModel(page, false));

            }
        });
    }
}