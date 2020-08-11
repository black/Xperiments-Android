package com.example.recyclerviewgrids.Demo3;

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

public class Demo3Fragment extends Fragment {
    private List<String> keysList = new ArrayList<>();
    private String[] keyVal = {"+", "+", "+", "+", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L",
            "Z", "X", "C", "V", "B", "N", "M","del", "tts", "done", "0","space","9",
            "1", "2", "3", "4", "5", "6", "7", "8", };

    private RV3Adapter rvAdapter;
    public Demo3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < keyVal.length; i++) {
            keysList.add(keyVal[i]);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_view_3);
        rvAdapter = new RV3Adapter(keysList,getContext());
        recyclerView.setAdapter(rvAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 8);
        recyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <4) {
                    return 2;
                } else if (position==30 || position==31 || position==32) {
                    return 2;
                }else if (position==34) {
                    return 6;
                }else{
                    return 1;
                }
            }
        });

        rvAdapter.setOnRVItemClickListener(new OnRVThreeItemClickListener() {
            @Override
            public void onClickListener(int pos) {

            }
        });
    }
}