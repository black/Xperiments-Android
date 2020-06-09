package com.example.examplelistview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView listView;
    private ListViewAdapter listViewAdapter;
    private List<ItemDataModel> itemList;

    private String[] titles = {"ONE","TWO","THREE","FOUR","FIVE","SIX"};
    private String[] descriptions = {"One item","Two item","Three item","Four item","Five item","Six item"};
    private int[] progresses = {5,5,5,5,5,5};

    long timeDelay = 1000; // Set this to your random number.
    Handler handler = new Handler();
    boolean mStopHandler = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.dynamicList);
        itemList = new ArrayList<>();

        /* Add initial Data */
        for(int i=0;i<6;i++){
            ItemDataModel itemDataModel = new ItemDataModel(titles[i],descriptions[i],progresses[i]);
            itemList.add(itemDataModel);
        }

        listViewAdapter = new ListViewAdapter(this,itemList);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Product",view.getTag()+" ");
            }
        });

        /* Dummy Data Generator*/
       // handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* Updating progress */
            for(int i=0;i<itemList.size();i++){
                int data = (int) (Math.random()*100);
                ItemDataModel itemDataModel = itemList.get(i);
                itemDataModel.setProgress(data);
            }
            listViewAdapter.notifyDataSetChanged();
            if (!mStopHandler) {
                handler.postDelayed(this, 1000);
            }
        }
    };

}
