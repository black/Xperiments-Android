package com.experiments.recyclerviewanimationwithviewpager.Demo2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.experiments.recyclerviewanimationwithviewpager.R;

import java.util.ArrayList;
import java.util.List;

public class Demo2Fragment extends Fragment {
    private List<String> keysList = new ArrayList<>();
    private String[] keyVal =  {"+", "+", "+", "+", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L",
            "Z", "X", "C", "V", "B", "N", "M","del", "tts", "done", "0","space","9",
            "1", "2", "3", "4", "5", "6", "7", "8", };

    private RecyclerView recyclerView;
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
        recyclerView = view.findViewById(R.id.rv_view_2);
        rvAdapter = new RV2Adapter(keysList,getContext());
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

        rvAdapter.setOnRVItemClickListener(new OnRVTwoItemClickListener(){
            @Override
            public void onClickListener(int pos) {

            }
        });
    }

    private void layoutAnimation(RecyclerView rv){
        Context context = rv.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_anim_2);
        rv.setLayoutAnimation(layoutAnimationController);
        rv.getAdapter().notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutAnimation(recyclerView);
    }
}