package com.black.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DataObject> objectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0;i<10;i++){
            objectList.add(new DataObject("title_"+i,"description_"+i,""));
        }

        recyclerView = findViewById(R.id.customRecyclerView);
        rvAdapter = new RecyclerViewAdapter(objectList);
        recyclerView.setAdapter(rvAdapter);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <2) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        rvAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnitemClick(int pos) {
                Log.d("ITEM_POS",objectList.get(pos).toString());
            }
        });
    }
}
