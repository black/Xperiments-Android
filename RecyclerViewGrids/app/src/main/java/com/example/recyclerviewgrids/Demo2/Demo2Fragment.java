package com.example.recyclerviewgrids.Demo2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewgrids.R;

import java.util.ArrayList;
import java.util.List;

public class Demo2Fragment extends Fragment {
    private List<String> keysList = new ArrayList<>();
    private String[] keyVal = {"+", "+", "+", "+", "+", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L", "del",
            "Z", "X", "C", "V", "B", "N", "M", "space", "tts", "done",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private RV2Adapter rvAdapter;
    public Demo2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < keyVal.length; i++) {
            keysList.add(keyVal[i]);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_view_2);
        rvAdapter = new RV2Adapter(keysList,getContext());
        recyclerView.setAdapter(rvAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 10);
        recyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <5) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        rvAdapter.setOnRVItemClickListener(new OnRVTwoItemClickListener(){
            @Override
            public void onClickListener(int pos) {

            }
        });
    }
}