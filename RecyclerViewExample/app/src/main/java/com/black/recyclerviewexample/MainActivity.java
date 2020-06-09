package com.black.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DataObject> objectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView contentRecyclerView = findViewById(R.id.customRecyclerView);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(int i=0;i<10;i++){
            objectList.add(new DataObject("title_"+i,"description_"+i,""));
        }

        contentRecyclerView.setAdapter(new CustomView(objectList));
    }
}
