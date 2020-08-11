package com.example.recyclerviewgrids.Demo1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewgrids.R;

import java.util.ArrayList;
import java.util.List;

public class Demo1Fragment extends Fragment {
    private List<String> keysList = new ArrayList<>();
    private String[] keyVal = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L", "del",
            "Z", "X", "C", "V", "B", "N", "M", "space", "tts", "done",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private RV1Adapter rvAdapter;
    public Demo1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < keyVal.length; i++) {
            keysList.add(keyVal[i]);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_view_1);
        rvAdapter = new RV1Adapter(keysList,getContext());
        recyclerView.setAdapter(rvAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(mLayoutManager);


        rvAdapter.setOnRVItemClickListener(new OnRVOneItemClickListener() {
            @Override
            public void onClickListener(int pos) {

            }
        });
    }
}